package com.jerry.duplication.repository

import CommonError
import arrow.core.Either

interface FindDuplicationRunningPolicyRepository {
    suspend operator fun invoke(policy: String): Either<CommonError.DataSourceError, Any?>
}
