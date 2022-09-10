package com.khs.movies.network.response.person

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetPopularPersonsResponse(
    val page: Int,
    val results: List<ListingPersonJson>,
    val total_results: Int,
    val total_pages: Int
)