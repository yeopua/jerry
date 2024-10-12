package com.jerry.search.place.webclient.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.search.place.domain.Place
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import org.springframework.stereotype.Repository
import toNonEmptyString

@Repository
class FindAllPlaceByKeywordWebClientAdapter(
    private val findAllPlaceByKeywordWebClientAdapterForKakao: FindAllPlaceByKeywordWebClientAdapterForKakao,
    private val findAllPlaceByKeywordWebClientAdapterForNaver: FindAllPlaceByKeywordWebClientAdapterForNaver
) : FindAllPlaceByKeywordRepository {
    override suspend fun invoke(keyword: String): Either<CommonError, Place> = either {
        Place("name".toNonEmptyString().bind(), "address".toNonEmptyString().bind())
//        placeWebClientMapper.toDomain(
//            placeWebClientResponse = PlaceWebClientResponse(name = "name", address = "address")
//        ).bind()
    }
}
