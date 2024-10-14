package com.jerry.common.webclient.openapi.naver

import CommonError
import JsonUtils.toMultiValueMap
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.webclient.WebClientObjectMapper
import com.jerry.common.webclient.openapi.OpenApiWebClient
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class NaverOpenApiCallService(
    private val webClient: OpenApiWebClient
) {
    private val header = NaverRequestHeader()
    private val mapper = WebClientObjectMapper.naver

    suspend fun <R : Any> executeGet(
        naverHostUri: NaverHostUri,
        queryParams: Any?,
        responseKClass: KClass<R>
    ): Either<CommonError, R> = either {
        webClient.executeGet(
            header = header.toMultiValueMap()
                .mapLeft { CommonError.ConversionError(it.message) }.bind(),
            mapper = mapper,
            hostUri = naverHostUri.toHostUri(),
            queryParams = queryParams.toMultiValueMap()
                .mapLeft { CommonError.ConversionError(it.message) }.bind(),
            responseKClass = responseKClass
        )
            .mapLeft { CommonError.DataSourceError(it.message) }.bind()
    }
}
