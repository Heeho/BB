package ru.ltow.bb.udp

import ru.ltow.bb.udp.common.Event
import java.net.InetSocketAddress
import java.util.ArrayList

class Connection (
) {
    var connected: Boolean = false
    var clientid: Int? = null
    var receiveraddress: InetSocketAddress? = null
    var receiverpublickey: Long? = null

    var lastreceivetimestamp: Long? = null
    var lastsendtimestamp: Long? = null

    val events = ArrayList<Event>()

    override fun toString(): String = "$receiveraddress, $receiverpublickey, $clientid, $connected, $lastreceivetimestamp, $lastsendtimestamp"
}