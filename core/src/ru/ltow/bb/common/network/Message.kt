package ru.ltow.bb.common.network

class Message: Packet() {
    enum class Type(val id: Int) {
        LOCAL(0),
        WHISPER(1)
    }

    val receiver = ""
    val text = ""

    override fun toString(): String = "to $receiver: $text"
}