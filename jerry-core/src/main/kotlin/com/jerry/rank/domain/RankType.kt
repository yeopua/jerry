package com.jerry.rank.domain

enum class RankType {
    SEARCH_PLACE
    ;

    companion object {
        fun getRankType(name: String) = entries.first { it.name == name }
    }
}
