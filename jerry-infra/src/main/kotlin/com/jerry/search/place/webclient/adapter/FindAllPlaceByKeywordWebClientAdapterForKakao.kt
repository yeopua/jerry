package com.jerry.search.place.webclient.adapter

import CommonError
import JsonUtils.toMultiValueMap
import JsonUtils.toSnakeCaseMultiValueMap
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.webclient.WebClientObjectMapper
import com.jerry.common.webclient.openapi.OpenApiWebClient
import com.jerry.common.webclient.openapi.kakao.KakaoHostUri
import com.jerry.common.webclient.openapi.kakao.KakaoRequestHeader
import com.jerry.common.webclient.openapi.kakao.KakaoWebClient
import com.jerry.common.webclient.openapi.kakao.toHostUri
import com.jerry.search.place.domain.Place
import com.jerry.search.place.webclient.mapper.PlaceWebClientMapper
import com.jerry.search.place.webclient.request.PlaceWebClientRequestForKakao
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForKakao
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapterForKakao(
    private val webClient: KakaoWebClient,
    private val mapper: PlaceWebClientMapper
) {
    suspend fun invoke(keyword: String): Either<CommonError, List<Place>> = either {
        webClient.executeGet(
            kakaoHostUri = KakaoHostUri.SEARCH_PLACES_BY_KEYWORD,
            queryParams = PlaceWebClientRequestForKakao.QueryParams(keyword),
            responseKClass = PlaceWebClientResponseForKakao::class
        )
            .bind()
            .let { mapper.toDomain(it) }.bind()
    }
}
