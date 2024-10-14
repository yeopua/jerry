package com.jerry.duplication.repository

import CommonError
import arrow.core.Either
import kotlin.time.Duration

interface SaveDuplicationRunningPolicyRepository {
    suspend operator fun invoke(policy: String, ttl: Duration): Either<CommonError.DataSourceError, Unit>
}
