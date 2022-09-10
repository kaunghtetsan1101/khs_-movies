package com.khs.movies.domain.model.person

data class Person(
    val id: Int,
    val profilePath: String?,
    val adult: Boolean,
    val name: String,
    val popularity: Float,
    val detail: PersonDetail?
)