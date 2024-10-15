package com.jerry.api.rank.dto

import com.jerry.rank.domain.Rank

class RankResponseDto private constructor(
    val type: String,
    val members: List<MemberDto>
) {
    class MemberDto(
        val value: String,
        val score: String
    )

    companion object {
        fun from(rank: Rank): RankResponseDto {
            return rank.members
                .map {
                    MemberDto(
                        value = it.value.toString(),
                        score = it.score.toString()
                    )
                }
                .let { RankResponseDto(rank.type.value, it) }
        }
    }
}
