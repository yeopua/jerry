package com.jerry.schedule.rank

import CommonError
import arrow.core.raise.either
import com.jerry.common.CheckRunning
import com.jerry.common.DuplicationRunningPolicy
import com.jerry.rank.repository.FindAllRankRepository
import com.jerry.rank.repository.SaveAllRankRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledRankService(
    private val findAllRankRepository: FindAllRankRepository,
    private val saveAllRankRepository: SaveAllRankRepository
) {
    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    @CheckRunning(policy = DuplicationRunningPolicy.RANK_SYNC)
    suspend fun sync() = either {
        val rank = findAllRankRepository.invoke()
            .mapLeft { CommonError.DataSourceError(it.message) }
            .bind()

        saveAllRankRepository.invoke(rank)
            .mapLeft { CommonError.DataSourceError(it.message) }
            .bind()
    }
}
