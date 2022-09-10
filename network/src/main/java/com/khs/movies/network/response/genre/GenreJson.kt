package com.khs.movies.network.response.genre

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreJson(
    val id: Int,
    val name: String
)