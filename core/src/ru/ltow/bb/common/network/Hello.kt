package ru.ltow.bb.common.network

class Hello(
    var clientid: Long = 0
): Packet() {
    init {
        packetType = Type.HELLO.id
    }
}