package com.jerry.rank.repository

import CommonError
import arrow.core.Either
import com.jerry.rank.domain.Rank

interface FindAllRankRepository {
    suspend operator fun invoke(): Either<CommonError, Rank>
}
