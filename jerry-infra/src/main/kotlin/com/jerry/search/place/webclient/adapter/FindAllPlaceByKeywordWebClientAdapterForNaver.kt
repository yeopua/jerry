package com.jerry.search.place.webclient.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.webclient.openapi.naver.NaverHostUri
import com.jerry.common.webclient.openapi.naver.NaverOpenApiCallService
import com.jerry.search.place.domain.Place
import com.jerry.search.place.webclient.mapper.PlaceWebClientMapper
import com.jerry.search.place.webclient.request.PlaceWebClientRequestForNaver
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForNaver
import getLogger
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapterForNaver(
    private val webClient: NaverOpenApiCallService,
    private val mapper: PlaceWebClientMapper
) {
    @Cacheable("findAllPlaceByKeywordNaver")
    suspend fun invoke(keyword: String): Either<CommonError, List<Place>> = either {
        webClient.executeGet(
            naverHostUri = NaverHostUri.SEARCH_PLACES_BY_KEYWORD,
            queryParams = PlaceWebClientRequestForNaver.QueryParams(keyword),
            responseKClass = PlaceWebClientResponseForNaver::class
        )
            .onLeft { logger.warn("[FindAllPlaceByKeywordWebClientAdapterForNaver][invoke] ${it.message}") }
            .bind()
            .let { mapper.toDomain(it) }.bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
