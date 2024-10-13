package com.jerry.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

class RedisConfiguration(
    @Value("\${spring.data.redis.host}") private val host: String,
    @Value("\${spring.data.redis.port}") private val port: Int
) {

    @Bean
    fun reactiveRedisConnectionFactory(): ReactiveRedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun reactiveRedisTemplate(
        @Qualifier("reactiveRedisConnectionFactory") connectionFactory: ReactiveRedisConnectionFactory
    ): ReactiveRedisTemplate<String, Any> {
        return StringRedisSerializer().let {
            val redisSerializer = Jackson2JsonRedisSerializer(Any::class.java).apply {
                JsonMapper.builder()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            }
            RedisSerializationContext.newSerializationContext<String, Any>()
                .key(it)
                .hashKey(it)
                .value(redisSerializer)
                .hashValue(redisSerializer)
                .build()
        }.let {
            ReactiveRedisTemplate(connectionFactory, it)
        }
    }
}
