package com.khs.movies.network.response.review

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewJson(
    val id: String,
    val author: String,
    val content: String,
    val url: String
)