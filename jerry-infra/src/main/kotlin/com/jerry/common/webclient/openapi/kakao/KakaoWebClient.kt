package com.jerry.common.webclient.openapi.kakao

import CommonError
import JsonUtils.toSnakeCaseMultiValueMap
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.webclient.WebClientObjectMapper
import com.jerry.common.webclient.openapi.OpenApiWebClient
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class KakaoWebClient(
    private val webClient: OpenApiWebClient
) {
    private val header = KakaoRequestHeader()
    private val mapper = WebClientObjectMapper.kakao

    suspend fun <R : Any> executeGet(
        kakaoHostUri: KakaoHostUri,
        queryParams: Any?,
        responseKClass: KClass<R>
    ): Either<CommonError, R> = either {
        webClient.executeGet(
            header = header.toSnakeCaseMultiValueMap()
                .mapLeft { CommonError.ConversionError(it.message) }.bind(),
            mapper = mapper,
            hostUri = kakaoHostUri.toHostUri(),
            queryParams = queryParams.toSnakeCaseMultiValueMap()
                .mapLeft { CommonError.ConversionError(it.message) }.bind(),
            responseKClass = responseKClass
        )
            .mapLeft { CommonError.DataSourceError(it.message) }.bind()
    }
}
