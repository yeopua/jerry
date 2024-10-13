package com.jerry.config

import org.h2.tools.Server
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener

class H2ConsoleConfiguration {
    private val port = "8081"
    private var server = Server()

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        server = Server.createWebServer("-webPort", port).start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        server.stop()
    }
}
