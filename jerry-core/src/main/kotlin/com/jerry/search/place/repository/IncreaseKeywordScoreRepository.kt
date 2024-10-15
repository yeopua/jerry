package com.jerry.search.place.repository

import CommonError
import arrow.core.Either
import com.jerry.rank.domain.RankType

interface IncreaseKeywordScoreRepository {
    suspend operator fun invoke(type: RankType, keyword: String): Either<CommonError.DataSourceError, Unit>
}
