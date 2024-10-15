package com.jerry.rank.domain

enum class RankType(
    val value: String
) {
    SEARCH_PLACE("search-place")
    ;

    companion object {
        fun getRankType(name: String) = entries.first { it.name == name }
    }
}
