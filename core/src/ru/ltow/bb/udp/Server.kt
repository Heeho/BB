package ru.ltow.bb.udp

import ru.ltow.bb.udp.common.Application
import ru.ltow.bb.udp.common.Packet
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.SocketTimeoutException
import kotlin.collections.ArrayList

class Server: Application(DatagramSocket(serverport)) {
  private val connections = ArrayList<Connection>()

  override fun emit() {
    for(connection in connections.shuffled()) {
      when(connection.connected) {
        true -> {
          send(connection.events.first(),connection.receiverpublickey)
        }
        else -> {}
      }
    }
  }

  private fun purge() {
    connections.removeIf { receivetimestamp - (it.lastreceivetimestamp?:0) > receivetimeout }
  }

  override fun receive() {
    purge()

    try {
      socket.receive(inpacket)
    } catch(ste: SocketTimeoutException) {
      println(ste)
      return
    }

    if(decodepacket()) return

    outpacket.address = inpacket.address
    outpacket.port = inpacket.port

    val peer = connections.find { c -> c.receiveraddress?.address == inpacket.address }

    if(peer == null) {
      when(packet.type) {
        Packet.Type.HELLO.id -> {
          if(connections.size < maxconnections) {
            hello.deserialize(decoder)
            val newclientid = newclientid()
            val newconnection = Connection().apply {
              this.clientid = newclientid
              this.receiveraddress = InetSocketAddress(inpacket.address,inpacket.port)
              this.receiverpublickey = hello.clientpublickey
              this.lastreceivetimestamp = receivetimestamp
            }
            connections.add(newconnection)
          }
        }
        else -> {}
      }
    } else {
      when(peer.connected) {
        true -> {
          peer.lastreceivetimestamp = receivetimestamp
          when (packet.type) {
            Packet.Type.EVENT.id -> {
              event.deserialize(decoder)
              ack.version = event.version
              send(ack,peer.receiverpublickey)
            }
            Packet.Type.ACK.id -> {
              ack.deserialize(decoder)
              peer.events.removeIf { it.version == ack.version }
            }
            Packet.Type.BYE.id -> {
              peer.connected = false
            }

            else -> {
              //println("received wrong packet: $packetmeta")
            }
          }
        }
        false -> {
          when(packet.type) {
            Packet.Type.HELLO.id -> {
              hello.deserialize(decoder)
            }
            else -> {
              //println("received wrong packet, client is already connected: $packetmeta")
            }
          }
        }
      }
    }
  }

  override fun getappstatus() {
    super.getappstatus()
    println("--CONNECTIONS")
    connections.forEach { println(it) }
  }

  fun connections() = connections.toList()
}
