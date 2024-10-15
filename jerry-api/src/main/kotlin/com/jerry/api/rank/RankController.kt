package com.jerry.api.rank

import com.jerry.api.rank.dto.RankResponseDto
import com.jerry.common.ApiResponse
import com.jerry.rank.domain.RankType
import org.springframework.core.convert.converter.Converter
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/rank")
class RankController(
    private val rankService: RankService
) {
    @GetMapping("/{type}")
    suspend fun getRank(
        @PathVariable("type") type: RankType
    ): ResponseEntity<ApiResponse<RankResponseDto>> {
        return rankService.getRank(type)
            .fold(
                { ApiResponse.error(it.message) },
                { ApiResponse.ok(it) }
            )
    }
}

@Component
class RankTypeConverter : Converter<String, RankType> {
    override fun convert(source: String): RankType? {
        return RankType.values().find { it.value.equals(source) }
    }
}
