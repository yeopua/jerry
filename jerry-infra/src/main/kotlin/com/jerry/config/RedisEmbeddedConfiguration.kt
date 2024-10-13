package com.jerry.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import redis.embedded.RedisServer
import java.io.File
import java.util.Objects

class RedisEmbeddedConfiguration(
    @Value("\${spring.data.redis.port}") private val port: Int
) {
    private lateinit var redisServer: RedisServer

    @PostConstruct
    fun start() {
        redisServer =
            if (isArmMac()) {
                RedisServer(getRedisFileForArcMac(), port)
            } else {
                RedisServer(port)
            }

        redisServer.start()
    }

    @PreDestroy
    fun stop() {
        redisServer.stop()
    }

    private fun isArmMac(): Boolean {
        return (
            Objects.equals(System.getProperty("os.arch"), "aarch64") &&
                Objects.equals(System.getProperty("os.name"), "Mac OS X")
            )
    }

    private fun getRedisFileForArcMac(): File {
        try {
            return ClassPathResource("redis/redis-server").file
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }
}
