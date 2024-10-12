package com.jerry.search.place.webclient.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.search.place.domain.Place
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import com.jerry.search.place.webclient.mapper.PlaceWebClientMapper
import com.jerry.search.place.webclient.response.PlaceWebClientResponse
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapter(
    private val placeWebClientMapper: PlaceWebClientMapper
) : FindAllPlaceByKeywordRepository {
    override suspend fun invoke(keyword: String): Either<CommonError, Place> = either {
        placeWebClientMapper.toDomain(
            placeWebClientResponse = PlaceWebClientResponse(name = "name", address = "address")
        ).bind()
    }
}
