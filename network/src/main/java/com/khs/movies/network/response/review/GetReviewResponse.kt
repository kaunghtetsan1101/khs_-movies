package com.khs.movies.network.response.review

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetReviewResponse(
    val id: Int,
    val page: Int,
    val results: List<ReviewJson>,
    val total_pages: Int,
    val total_results: Int
)