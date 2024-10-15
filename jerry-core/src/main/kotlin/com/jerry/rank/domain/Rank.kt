package com.jerry.rank.domain

import NonEmptyString

class Rank(
    val type: RankType,
    val members: List<Member>
) {
    class Member(
        val value: NonEmptyString,
        val score: Int
    )
}
