package com.jerry.common.webclient.openapi

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.either
import com.fasterxml.jackson.databind.ObjectMapper
import com.jerry.common.webclient.HostUri
import com.jerry.config.WebClientConfiguration.Companion.MEGA_BYTE
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.util.UriUtils
import reactor.core.publisher.Mono
import kotlin.reflect.KClass
import kotlin.time.toJavaDuration

@Service
class OpenApiWebClient(
    private val webClient: WebClient,
    @Value("\${webclient.config.max-in-memory-size}") private val maxInMemorySize: Int
) {
    suspend fun <R : Any> executeGet(
        header: MultiValueMap<String, String>,
        hostUri: HostUri,
        mapper: ObjectMapper,
        queryParams: MultiValueMap<String, String>,
        responseKClass: KClass<R>
    ): Either<OpenApiWebClientError, R> =
        getOpenApiWebClient(mapper)
            .get()
            .uri(hostUri.domain) {
                it.path(hostUri.uri).queryParams(UriUtils.encodeQueryParams(queryParams)).build()
            }
            .headers { httpHeaders ->
                httpHeaders.addAll(header)
                httpHeaders.accept = listOf(MediaType.APPLICATION_JSON)
            }
            .retrieve()
            .onStatus({ !it.is2xxSuccessful }) { response ->
                response.bodyToMono<String>().flatMap { body ->
                    Mono.error(
                        RuntimeException("[OpenApiWebClient][executeGet] fail, statusCode=${response.statusCode()}, responseBody=$body")
                    )
                }
            }
            .bodyToMono(responseKClass.java)
            .timeout(hostUri.timeout.toJavaDuration())
            .map { either<OpenApiWebClientError, R> { it } }
            .onErrorResume {
                Mono.just(
                    OpenApiWebClientError.UnknownError(it.message ?: "WebClient executeGet Error").left()
                )
            }
            .awaitSingle()

    private fun getOpenApiWebClient(mapper: ObjectMapper): WebClient =
        ExchangeStrategies.builder().codecs {
            it.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON))
            it.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON))
            it.defaultCodecs().maxInMemorySize(MEGA_BYTE * maxInMemorySize)
        }
            .build()
            .let { webClient.mutate().exchangeStrategies(it).build() }
}

sealed class OpenApiWebClientError {
    abstract val message: String
    data class UnknownError(override val message: String) : OpenApiWebClientError()
}
