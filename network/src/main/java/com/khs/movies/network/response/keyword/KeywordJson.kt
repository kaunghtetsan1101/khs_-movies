package com.khs.movies.network.response.keyword

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class KeywordJson(
    val id: Int,
    val name: String
)