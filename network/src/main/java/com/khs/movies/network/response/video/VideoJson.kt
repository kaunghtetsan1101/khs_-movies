package com.khs.movies.network.response.video

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VideoJson(
    val id: String,
    val name: String,
    val site: String,
    val key: String,
    val size: Int,
    val type: String
)