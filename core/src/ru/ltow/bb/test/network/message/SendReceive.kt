package ru.ltow.bb.test.network.message

import ru.ltow.bb.common.network.Packet
import java.io.*
import java.lang.Thread.sleep
import javax.net.ServerSocketFactory
import javax.net.SocketFactory
import kotlin.concurrent.thread

fun main() {
    val server = ServerSocketFactory.getDefault().createServerSocket(9999)
    var listening = true

    val t = thread {
        while(listening) {
            println("started listening...")
            val i = DataInputStream(server.accept().inputStream)
            println("socket accepted")
            while(listening) {
                try {
                    val type = i.readByte().toInt()
                    when(type) {
                        Packet.Type.MESSAGE.id -> {
                            println(i.readUTF())
                        }
                    }
                    sleep(55)
                } catch(e: EOFException) {
                    continue
                }
            }
        }
    }

    sleep(1111)

    println("connecting to server...")
    val client = SocketFactory.getDefault().createSocket("127.0.0.1",9999)
    println("connected")

    sleep(1111)

    val o = DataOutputStream(client.outputStream)
    val message = "hello!"
    repeat(3) {
        o.writeByte(Packet.Type.MESSAGE.id)
        o.writeUTF(message)
        o.writeUTF("2nd string shouldn't be printed")
        println("message sent")
        sleep(1111)
    }

    listening = false

    server.close()
    client.close()
}