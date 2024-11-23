package ru.ltow.bb.test.network

import java.io.FileInputStream
import java.lang.Thread.sleep
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLServerSocket
import javax.net.ssl.SSLSocket
import javax.net.ssl.X509TrustManager
import kotlin.concurrent.thread

fun main(
    args: Array<String> = arrayOf(
        "C:\\dst\\bb.jks",
        "123abc"
    )
) {
    val kystorepath = args[0]
    val keystorepassword = args[1].toCharArray()

    val protocol = "TLS"
    val algorithm = "SunX509"
    val keystoretype = "JKS"

    //SERVER

    val server = SSLContext.getInstance(protocol).apply {
        this.init(
            (KeyManagerFactory.getInstance(algorithm)).apply {
                this.init(
                    KeyStore.getInstance(keystoretype).apply {
                        this.load(
                            FileInputStream(kystorepath),
                            keystorepassword
                        )
                    },
                    keystorepassword
                )
            }.keyManagers,
            null,
            null
        )
    }.serverSocketFactory.createServerSocket(9999) as SSLServerSocket

    server.enabledCipherSuites = server.supportedCipherSuites
    server.enabledProtocols = server.supportedProtocols

    var listening = true

    val t = thread {
        while(listening) {
            println("Listening...")
            val clientsocket = server.accept() as SSLSocket
            println("Socket accepted. Starting handshake...")
            clientsocket.startHandshake()
            println("Server: Handshake finished")
        }
    }

    sleep(1111)

    //CLIENT
    println("Connecting to server...")
    val client = SSLContext.getInstance(protocol).apply {
        this.init(
            null,
            arrayOf(
                object :X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                }
            ),
            SecureRandom()
        )
    }.socketFactory.createSocket("127.0.0.1",9999) as SSLSocket

    println("Connected. Starting handshake...")
    client.startHandshake()
    println("Client: Handshake finished")

    listening = false

    server.close()
    client.close()
}