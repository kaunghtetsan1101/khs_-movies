package com.khs.movies.domain.model.person

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PersonDetail(
    val birthday: String?,
    val knownForDepartment: String,
    val placeOfBirth: String?,
    val alsoKnownAs: List<String>,
    val biography: String
)