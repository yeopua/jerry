package com.jerry.search.place.webclient.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.webclient.openapi.kakao.KakaoHostUri
import com.jerry.common.webclient.openapi.kakao.KakaoOpenApiCallService
import com.jerry.search.place.domain.Place
import com.jerry.search.place.webclient.mapper.PlaceWebClientMapper
import com.jerry.search.place.webclient.request.PlaceWebClientRequestForKakao
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForKakao
import getLogger
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapterForKakao(
    private val webClient: KakaoOpenApiCallService,
    private val mapper: PlaceWebClientMapper
) {
    suspend fun invoke(keyword: String): Either<CommonError, List<Place>> = either {
        coroutineScope {
            webClient.executeGet(
                kakaoHostUri = KakaoHostUri.SEARCH_PLACES_BY_KEYWORD,
                queryParams = PlaceWebClientRequestForKakao.QueryParams(keyword),
                responseKClass = PlaceWebClientResponseForKakao::class
            )
                .onLeft { logger.warn("[FindAllPlaceByKeywordWebClientAdapterForKakao][invoke] ${it.message}") }
                .bind()
                .let { mapper.toDomain(it) }.bind()
        }
    }

    companion object {
        private val logger = getLogger()
    }
}
