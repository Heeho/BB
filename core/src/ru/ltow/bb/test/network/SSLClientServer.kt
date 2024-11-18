package ru.ltow.bb.test.network

import java.lang.Thread.sleep
import javax.net.ssl.SSLServerSocket
import javax.net.ssl.SSLServerSocketFactory
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory
import kotlin.concurrent.thread

fun main() {
    //TODO: RSA,keyManager,SSLContext
    val server = SSLServerSocketFactory.getDefault().createServerSocket(9999) as SSLServerSocket

    val suites =  server.supportedCipherSuites
    println("cipher suites: $suites")
    server.enabledCipherSuites = suites

    val protocols = server.supportedProtocols
    println("protocols: $protocols")
    server.enabledProtocols = protocols

    var listening = true

    val t = thread {
        while(listening) {
            println("Listening...")
            val clientsocket = server.accept() as SSLSocket
            println("Socket accepted. Starting handshake...")
            clientsocket.startHandshake()
            println("Handshake finished")
        }
    }

    sleep(1111)

    //TODO: trustManager,SSLContext
    println("Connecting to server...")
    val client = SSLSocketFactory.getDefault().createSocket("127.0.0.1",9999) as SSLSocket
    println("Connected. Starting handshake...")
    client.startHandshake()
    println("connected?")

    sleep(11111)

    listening = false

    server.close()
    client.close()
}