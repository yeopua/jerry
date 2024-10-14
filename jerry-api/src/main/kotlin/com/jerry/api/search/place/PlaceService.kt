package com.jerry.api.search.place

import arrow.core.Either
import arrow.core.raise.either
import com.jerry.api.search.place.dto.PlaceResponseDto
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.repository.FindAllRankRepository
import com.jerry.rank.repository.SaveAllRankRepository
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import org.springframework.stereotype.Service
import toNonEmptyString

@Service
class PlaceService(
    private val findAllPlaceByKeywordRepository: FindAllPlaceByKeywordRepository,
    private val saveAllRankRepository: SaveAllRankRepository,
    private val findAllRankRepository: FindAllRankRepository
) {
    // TODO
    suspend fun getPlaces(keyword: String): Either<PlaceServiceError, PlaceResponseDto> = either {
        val rank = Rank(
            type = RankType.SEARCH_PLACE,
            member = listOf(
                Rank.Member("a".toNonEmptyString().mapLeft { PlaceServiceError.TempError(it.message) }.bind(), 2),
                Rank.Member("b".toNonEmptyString().mapLeft { PlaceServiceError.TempError(it.message) }.bind(), 3),
                Rank.Member("c".toNonEmptyString().mapLeft { PlaceServiceError.TempError(it.message) }.bind(), 4)
            )
        )

        saveAllRankRepository.invoke(rank)
            .mapLeft { PlaceServiceError.TempError(it.message) }.bind()

        findAllRankRepository.invoke()
            .mapLeft { PlaceServiceError.TempError(it.message) }.bind()

        findAllPlaceByKeywordRepository.invoke(keyword)
            .mapLeft {
                // TODO : 여기도 CommonError 케이스 있으면 나눠주기
                PlaceServiceError.TempError(it.message)
            }.bind()
            .let { PlaceResponseDto.from(it) }
    }
}

sealed class PlaceServiceError {
    abstract val message: String

    // TODO
    data class TempError(override val message: String) : PlaceServiceError()
}
