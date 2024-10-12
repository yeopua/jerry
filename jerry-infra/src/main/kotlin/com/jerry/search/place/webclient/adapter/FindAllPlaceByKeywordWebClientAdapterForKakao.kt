package com.jerry.search.place.webclient.adapter

import CommonError
import JsonUtils.toMultiValueMap
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.webclient.openapi.OpenApiWebClient
import com.jerry.common.webclient.openapi.kakao.KakaoHostUri
import com.jerry.common.webclient.openapi.kakao.KakaoRequestHeader
import com.jerry.common.webclient.openapi.kakao.toHostUri
import com.jerry.search.place.domain.Place
import com.jerry.search.place.webclient.mapper.PlaceWebClientMapper
import com.jerry.search.place.webclient.request.PlaceWebClientRequestForKakao
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForKakao
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapterForKakao(
    private val webClient: OpenApiWebClient,
    private val mapper: PlaceWebClientMapper
) {
    suspend fun invoke(keyword: String): Either<CommonError, List<Place>> = either {
        webClient.executeGet(
            header = KakaoRequestHeader(),
            hostUri = KakaoHostUri.SEARCH_PLACES_BY_KEYWORD.toHostUri(),
            queryParams = PlaceWebClientRequestForKakao.QueryParams(keyword).toMultiValueMap()
                .mapLeft { CommonError.ConversionError(it.message) }.bind(),
            responseKClass = PlaceWebClientResponseForKakao::class
        )
            .mapLeft { CommonError.DataSourceError(it.message) }.bind()
            .let {
                println(it)
                it
            }
            .let { mapper.toDomain(it) }.bind()
    }
}
