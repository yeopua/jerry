package com.jerry.rank.repository

import CommonError
import arrow.core.Either
import com.jerry.rank.domain.RankType

interface IncreaseKeywordScoreRepository {
    suspend operator fun invoke(type: RankType, keyword: String, score: Int = 1): Either<CommonError.DataSourceError, Int>
}
