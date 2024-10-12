package com.jerry.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.LoggingCodecSupport
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration
import java.util.concurrent.TimeUnit

@EnableConfigurationProperties(WebClientConfig::class)
class WebClientConfiguration(
    private val webClientConfig: WebClientConfig
) {
    @Bean
    fun webClient(): WebClient {
        val exchangeStrategies = ExchangeStrategies.builder().codecs { configurer ->
            configurer.defaultCodecs().maxInMemorySize(MEGA_BYTE * webClientConfig.maxInMemorySize)
        }.build()

        exchangeStrategies.messageWriters().filterIsInstance<LoggingCodecSupport>()
            .forEach { it.isEnableLoggingRequestDetails }

        val httpClient = ConnectionProvider.builder("aod-http-client").apply {
            maxConnections(webClientConfig.maxConnections)
            pendingAcquireTimeout(Duration.ofMillis(webClientConfig.pendingAcquireTimeout))
            pendingAcquireMaxCount(webClientConfig.pendingAcquireMaxCount)
            maxIdleTime(Duration.ofMillis(webClientConfig.maxIdleTime))
            maxLifeTime(Duration.ofMillis(webClientConfig.maxLifeTime))
        }
            .build()
            .let {
                HttpClient.create(it)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientConfig.connectChannelOptionTimeout)
                    .doOnConnected { conn ->
                        conn.apply {
                            addHandlerLast(
                                ReadTimeoutHandler(
                                    webClientConfig.connectReadTimeout,
                                    TimeUnit.MILLISECONDS
                                )
                            )
                            addHandlerLast(
                                WriteTimeoutHandler(
                                    webClientConfig.connectWriteTimeout,
                                    TimeUnit.MILLISECONDS
                                )
                            )
                        }
                    }
                    .responseTimeout(Duration.ofMillis(webClientConfig.responseTimeout))
                    .wiretap(true)
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .exchangeStrategies(exchangeStrategies)
            .filter(
                ExchangeFilterFunction.ofRequestProcessor { clientRequest: ClientRequest ->
                    Mono.just(clientRequest)
                }
            )
            .filter(
                ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
                    Mono.just(clientResponse)
                }
            )
            .uriBuilderFactory(
                DefaultUriBuilderFactory().apply { encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY }
            )
            .defaultRequest { it.accept(MediaType.APPLICATION_JSON) }
            .build()
    }

    companion object {
        const val MEGA_BYTE = 1024 * 1024
    }
}

@ConfigurationProperties(prefix = "webclient.config")
data class WebClientConfig(
    val maxInMemorySize: Int,
    val maxConnections: Int,
    val pendingAcquireTimeout: Long,
    val pendingAcquireMaxCount: Int,
    val maxIdleTime: Long,
    val maxLifeTime: Long,
    val responseTimeout: Long,
    val connectReadTimeout: Long,
    val connectWriteTimeout: Long,
    val connectChannelOptionTimeout: Int
)
