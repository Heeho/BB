package ru.ltow.bb.udp.common

open class Packet {
    enum class Type(val id: Byte) {
        HELLO(1),
        HERE(2),
        EVENT(3),
        ACK(4),
        BYE(5)
    }

    var protocolversion = Application.protocolversion
    var type: Byte = 0
    var requiresserverresponse = false

    open fun serialize(e: Encoder): Encoder = e.write(protocolversion).write(type)

    open fun deserialize(d: Decoder) {
        protocolversion = d.readInt()
        type = d.readByte()
    }

    override fun toString(): String = "$protocolversion, $type"
}

class Hello(
    var clientpublickey: Long = 0,
    var clientid: Long = 0
): Packet() {
    init {
        type = Type.HELLO.id
        requiresserverresponse = true
    }

    override fun serialize(e: Encoder): Encoder = super.serialize(e).write(clientpublickey).write(clientid)

    override fun deserialize(d: Decoder) {
        super.deserialize(d)
        clientpublickey = d.readLong()
        clientid = d.readLong()
    }
}

class Here(
    var clientid: Long = 0
): Packet() {
    init {
        type = Type.HERE.id
    }

    override fun serialize(e: Encoder): Encoder = super.serialize(e).write(clientid)

    override fun deserialize(d: Decoder) {
        super.deserialize(d)
        clientid = d.readLong()
    }
}

class Bye(
    var clientid: Long = 0
): Packet() {
    init {
        type = Type.BYE.id
    }

    override fun serialize(e: Encoder): Encoder = super.serialize(e).write(clientid)

    override fun deserialize(d: Decoder) {
        super.deserialize(d)
        clientid = d.readLong()
    }
}

class Event: Packet() {
    init {
        type = Type.EVENT.id
        requiresserverresponse = true
    }

    var clientid = 0L
    var version = 0L
    var data = 0

    override fun serialize(e: Encoder): Encoder = super.serialize(e).write(clientid).write(version).write(data)

    override fun deserialize(d: Decoder) {
        super.deserialize(d)
        clientid = d.readLong()
        version = d.readLong()
        data = d.readInt()
    }

    override fun toString(): String = super.toString()+", $version, $data"
}

class Ack:  Packet() {
    init {
        type = Type.ACK.id
    }

    var clientid = 0
    var version = 0L

    override fun serialize(e: Encoder): Encoder = super.serialize(e).write(clientid).write(version)

    override fun deserialize(d: Decoder) {
        super.deserialize(d)
        clientid = d.readInt()
        version = d.readLong()
    }

    override fun toString(): String = super.toString()+", $version"
}