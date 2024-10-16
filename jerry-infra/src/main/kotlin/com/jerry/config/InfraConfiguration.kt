package com.jerry.config

import org.springframework.context.annotation.Import

@Import(
    value = [
        WebClientConfiguration::class,
        R2dbcConfiguration::class,
        H2ConsoleConfiguration::class,
        RedisEmbeddedConfiguration::class,
        RedisConfiguration::class,
        CaffeineCacheConfiguration::class
    ]
)
class InfraConfiguration
