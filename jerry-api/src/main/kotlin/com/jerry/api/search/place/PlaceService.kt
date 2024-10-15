package com.jerry.api.search.place

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.api.search.place.dto.PlaceResponseDto
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val findAllPlaceByKeywordRepository: FindAllPlaceByKeywordRepository
) {
    suspend fun getPlaces(keyword: String): Either<CommonError, PlaceResponseDto> = either {
        findAllPlaceByKeywordRepository.invoke(keyword)
            .bind()
            .let { PlaceResponseDto.from(it) }
    }
}
