package com.jerry.search.place.webclient.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.search.place.domain.Place
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapter(
    private val findAllPlaceByKeywordWebClientAdapterForKakao: FindAllPlaceByKeywordWebClientAdapterForKakao,
    private val findAllPlaceByKeywordWebClientAdapterForNaver: FindAllPlaceByKeywordWebClientAdapterForNaver
) : FindAllPlaceByKeywordRepository {
    override suspend fun invoke(keyword: String): Either<CommonError, List<Place>> = either {
        findAllPlaceByKeywordWebClientAdapterForKakao.invoke(keyword).bind()
    }
}
