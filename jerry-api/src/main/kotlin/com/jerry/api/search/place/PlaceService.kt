package com.jerry.api.search.place

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.api.search.place.dto.PlaceResponseDto
import com.jerry.rank.domain.RankType
import com.jerry.rank.repository.IncreaseKeywordScoreRepository
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import com.jerry.search.place.repository.FindRankMemberByTypeAndKeyWordRepository
import getLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val findAllPlaceByKeywordRepository: FindAllPlaceByKeywordRepository,
    private val findRankMemberByTypeAndKeyWordRepository: FindRankMemberByTypeAndKeyWordRepository,
    private val increaseKeywordScoreRepository: IncreaseKeywordScoreRepository
) {
    suspend fun getPlaces(keyword: String): Either<CommonError, PlaceResponseDto> = either {
        coroutineScope {
            findAllPlaceByKeywordRepository.invoke(keyword)
                .onLeft { logger.error("[PlaceService][getPlaces] ${it.message}") }
                .onRight {
                    launch(Job() + Dispatchers.IO) {
                        val score = increaseKeywordScoreRepository.invoke(RankType.SEARCH_PLACE, keyword).bind()
                        findRankMemberByTypeAndKeyWordRepository.invoke(RankType.SEARCH_PLACE, keyword).bind()?.members
                            ?.first()?.score
                            ?.takeIf { it > score }
                            ?.run { increaseKeywordScoreRepository.invoke(RankType.SEARCH_PLACE, keyword, this) }
                    }
                }
                .bind()
                .let { PlaceResponseDto.from(it) }
        }
    }

    companion object {
        private val logger = getLogger()
    }
}
