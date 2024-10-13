package com.jerry.config

import org.h2.tools.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener

class H2ConsoleConfiguration(
    @Value("\${h2.console.port}") private val port: String
) {
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
