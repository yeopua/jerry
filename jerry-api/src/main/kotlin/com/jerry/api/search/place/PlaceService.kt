package com.jerry.api.search.place

import arrow.core.Either
import arrow.core.raise.either
import com.jerry.api.search.place.dto.PlaceResponseDto
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val findAllPlaceByKeywordRepository: FindAllPlaceByKeywordRepository
) {
    suspend fun getPlaces(keyword: String): Either<PlaceServiceError, PlaceResponseDto> = either {
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
