package ru.ltow.bb.udp

import ru.ltow.bb.udp.common.Application
import ru.ltow.bb.udp.common.Event
import ru.ltow.bb.udp.common.Packet
import java.net.DatagramSocket
import java.net.InetSocketAddress
import java.net.SocketTimeoutException

class Client: Application(DatagramSocket()) {
  private val connection: Connection = Connection().apply {
    this.receiveraddress = InetSocketAddress(serveraddress,serverport)
  }

  init {
    outpacket.socketAddress = connection.receiveraddress
    hello.clientpublickey = cryptor.publickey
  }

  override fun emit() {
    when(connection.connected) {
      false -> {
        connection.connected = true
        send(hello,null)
      }
      true -> {
        if(connection.events.isNotEmpty())
          send(connection.events.first(),connection.receiverpublickey)
        if(emittimestamp - (connection.lastsendtimestamp?:0) > keepaliveinterval) {
          send(here,connection.receiverpublickey)
          connection.lastsendtimestamp = emittimestamp
        }
      }
    }
  }

  override fun receive() {
    try {
      socket.receive(inpacket)
    } catch(ste: SocketTimeoutException) {
      println(ste)
      connection.connected = false
      return
    }

    if(!decodepacket()) return

    connection.lastreceivetimestamp = receivetimestamp

    when(connection.connected) {
      true -> {
        when(packet.type) {
          Packet.Type.EVENT.id -> {
            event.deserialize(decoder)
            connection.events.add(Event().apply { this.data = event.data })
          }
          Packet.Type.ACK.id -> {
            ack.deserialize(decoder)
            connection.events.removeIf { it.version == ack.version }
          }
        }
      }
      else -> {
        println("received wrong packet: $packet")
      }
    }
  }

  fun disconnect() {
    if(connection.connected) {
      for (i in 0..10) send(bye,connection.receiverpublickey)
      connection.connected = false
      stop()
    }
  }
}
