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

    val message = "hello!"

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
                            val receivedmessage = i.readUTF()
                            assert( message == receivedmessage )
                            println("message received: $receivedmessage")
                        }
                    }
                } catch(e: EOFException) {
                    continue
                }
            }
        }
    }

    val client = SocketFactory.getDefault().createSocket("127.0.0.1",9999)

    val o = DataOutputStream(client.outputStream)
    repeat(3) {
        o.writeByte(Packet.Type.MESSAGE.id)
        sleep(1111) //Не влияет: на сервере println(i.readUTF()) ждет данные. Синхронизация не нужна.
        o.writeUTF(message)
        o.writeUTF("2nd string shouldn't be printed")
        println("message sent")
    }

    sleep(1111)

    listening = false

    server.close()
    client.close()
}