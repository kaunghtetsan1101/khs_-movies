package com.khs.movies.domain.model.person

data class ListingPerson(
    val id: Int,
    val profilePath: String?,
    val adult: Boolean,
    val name: String,
    val popularity: Float
)