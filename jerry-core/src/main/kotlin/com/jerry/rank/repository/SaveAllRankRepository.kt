package com.jerry.rank.repository

import CommonError
import arrow.core.Either
import com.jerry.rank.domain.Rank

interface SaveAllRankRepository {
    suspend operator fun invoke(rank: Rank): Either<CommonError, Unit>
}
