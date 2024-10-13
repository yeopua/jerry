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

// public class H2ConsoleConfig {
//
//    @Value("${h2.console.port}")
//    private Integer port;
//
//    private Server webServer;
//
//    @EventListener(ContextRefreshedEvent.class)
//            public void start() throws java.sql.SQLException {
//        log.info("started h2 console at port {}.", port);
//        webServer = Server.createWebServer("-webPort", port.toString()).start();
//    }
//
//        @EventListener(ContextClosedEvent.class)
//                public void stop() {
//            log.info("stopped h2 console at port {}.", port);
//            webServer.stop();
//        }
// }
