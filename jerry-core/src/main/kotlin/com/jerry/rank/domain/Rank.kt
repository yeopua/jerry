package com.jerry.rank.domain

import NonEmptyString

data class Rank(
    val type: RankType,
    val members: List<Member>
) {
    data class Member(
        val value: NonEmptyString,
        val score: Int
    )
}

fun Rank.Member.modifyScore(score: Int): Rank.Member {
    return Rank.Member(
        value = this.value,
        score = score
    )
}
