package com.jerry.common

enum class CacheType(val cacheName: String, val expireAfterWrite: Long, val maximumSize: Long) {
    FIND_ALL_PLACE_BY_KEYWORD_KAKAO(
        "findAllPlaceByKeywordKakao",
        60 * 5, // 5min
        100 // keyword 100개까지만 Cache
    ),
    FIND_ALL_PLACE_BY_KEYWORD_NAVER(
        "findAllPlaceByKeywordNaver",
        60 * 5, // 5min
        100 // keyword 100개까지만 Cache
    ),
    FIND_RANK_TOP_TEN(
        "findRankTopTen",
        10, // 10sec
        30 // RankType 50개까지만 Cache
    )
    ;
}
