package com.khs.movies.network.response.person

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonJson(
    val id: Int,
    val profile_path: String?,
    val adult: Boolean,
    val name: String,
    val popularity: Float,
    val birthday: String?,
    val known_for_department: String,
    val place_of_birth: String?,
    val also_known_as: List<String>,
    val biography: String
)