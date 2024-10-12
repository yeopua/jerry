package com.jerry.search.place.repository

import CommonError
import arrow.core.Either
import com.jerry.search.place.domain.Place

interface FindAllPlaceByKeywordRepository {
    suspend operator fun invoke(keyword: String): Either<CommonError, List<Place>>
}
