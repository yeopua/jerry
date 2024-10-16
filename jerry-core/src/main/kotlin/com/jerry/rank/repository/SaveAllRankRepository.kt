package com.jerry.rank.repository

import CommonError
import arrow.core.Either
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType

interface SaveAllRankRepository {
    suspend operator fun invoke(type: RankType): Either<CommonError, Rank?>
}
