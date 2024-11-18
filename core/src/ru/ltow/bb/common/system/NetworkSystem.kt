package ru.ltow.bb.common.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.systems.IntervalSystem
import ru.ltow.bb.common.network.Packet
import java.io.*
import java.net.Socket

class NetworkSystem(
    socket: Socket,
    i: Float
): IntervalSystem(i) {
    val i = DataInputStream(socket.inputStream)
    val o = DataOutputStream(socket.outputStream)

    override fun addedToEngine(e: Engine) {

    }

    override fun updateInterval() {

    }

    fun send(d: Packet) {}

    fun receive() {
        val p = Packet().deserialize(i)
        when(p.packetType) {
            Packet.Type.HELLO.id -> {

            }
        }
    }
}