package ru.ltow.bb.common.network

class Data: Packet() {
    enum class Type(val id: Int) {
        MOVE(1),
        ACTION(2),
        CHANGE(3),
        LANDSCAPE(4)
    }
}