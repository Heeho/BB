package ru.ltow.bb.common.network

import java.io.DataInputStream
import java.io.DataOutputStream

open class Packet {
    enum class Type(val id: Int) {
        HELLO(0),
        BYE(1),
        DATA(2),
        MESSAGE(3)
    }

    var packetType: Int = 0

    open fun serialize(o: DataOutputStream) {
        o.writeByte(packetType)
    }

    open fun deserialize(i: DataInputStream): Packet {
        packetType = i.read()
        return this
    }

    override fun toString(): String = "$packetType"
}