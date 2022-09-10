package com.khs.movies.network.response.keyword

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetKeywordResponse(
    val id: Int,
    val keywords: List<KeywordJson>
)