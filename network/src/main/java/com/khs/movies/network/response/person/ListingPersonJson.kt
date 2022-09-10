package com.khs.movies.network.response.person

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListingPersonJson(
    val id: Int,
    val profile_path: String?,
    val adult: Boolean,
    val name: String,
    val popularity: Float
)