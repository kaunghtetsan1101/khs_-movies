package com.khs.movies.network.response.movie

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetDiscoverMovieResponse(
    val page: Int,
    val results: List<DiscoverMovieJson>,
    val total_results: Int,
    val total_pages: Int
)