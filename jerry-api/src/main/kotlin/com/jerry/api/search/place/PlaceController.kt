package com.jerry.api.search.place

import com.jerry.api.search.place.dto.PlaceResponseDto
import com.jerry.common.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search/place")
class PlaceController(
    private val placeService: PlaceService
) {
    @GetMapping
    suspend fun getPlaces(
        @RequestParam(name = "keyword") keyword: String
    ): ResponseEntity<ApiResponse<PlaceResponseDto>> =
        placeService.getPlaces(keyword).fold(
            { ApiResponse.error(it.message) },
            { ApiResponse.ok(it) }
        )
}
