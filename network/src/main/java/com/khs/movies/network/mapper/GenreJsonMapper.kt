package com.khs.movies.network.mapper

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.Genre
import com.khs.movies.network.response.genre.GenreJson
import javax.inject.Inject

class GenreJsonMapper @Inject constructor() : UniMapper<GenreJson, Genre> {

    override fun map(item: GenreJson): Genre = with(item) {
        Genre(id, name)
    }
}