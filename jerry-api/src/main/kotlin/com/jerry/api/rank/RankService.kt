package com.jerry.api.rank

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.api.rank.dto.RankResponseDto
import com.jerry.rank.domain.RankType
import com.jerry.rank.repository.FindAllRankTopTenRepository
import getLogger
import org.springframework.stereotype.Service

@Service
class RankService(
    private val findAllRankTopTenRankController: FindAllRankTopTenRepository
) {
    suspend fun getRank(type: RankType): Either<CommonError, RankResponseDto> = either {
        findAllRankTopTenRankController.invoke(type)
            .onLeft { logger.error("[RankService][getRank] ${it.message}") }
            .bind()
            .let { RankResponseDto.from(it) }
    }

    companion object {
        private val logger = getLogger()
    }
}
