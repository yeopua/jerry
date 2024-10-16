package com.jerry.schedule.rank

import CommonError
import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import com.jerry.common.CheckRunning
import com.jerry.common.DuplicationRunningPolicy
import com.jerry.rank.domain.RankType
import com.jerry.rank.repository.SaveAllRankRepository
import getLogger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledRankService(
    private val saveAllRankRepository: SaveAllRankRepository
) {

    //    @Scheduled(initialDelay = 600000, fixedDelay = 600000)
    @Scheduled(initialDelay = 5000, fixedDelay = 5000) // for test, 5000ms
    @CheckRunning(policy = DuplicationRunningPolicy.RANK_SYNC)
    suspend fun sync(): Either<CommonError, Unit> = either {
        catch({
            RankType.entries
                .forEach {
                    logger.info("[ScheduledRankService][sync] Start for $it")
                    saveAllRankRepository.invoke(it).bind()
                }
        }) {
            logger.error("[ScheduledRankService][sync] ${it.message} $it")
            raise(CommonError.DataSourceError("${it.message}"))
        }
    }

    companion object {
        private val logger = getLogger()
    }
}
