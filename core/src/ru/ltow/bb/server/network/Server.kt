package ru.ltow.bb.server.network

import ru.ltow.bb.common.Disposable
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class Server(
    val serversocket: ServerSocket
): Disposable {
    val clientsockets = ArrayList<Socket>()
    var connecting = true

    val connector = thread {
        while(connecting) {
            clientsockets.add(serversocket.accept())
        }
    }

    override fun dispose() {
        connecting = false
        clientsockets.forEach {
            it.close()
        }
    }
}
