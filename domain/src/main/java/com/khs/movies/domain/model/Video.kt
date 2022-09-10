package com.khs.movies.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Video(
    val id: String,
    val name: String,
    val site: String,
    val key: String,
    val size: Int,
    val type: String
)