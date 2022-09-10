package com.khs.movies.cache.mapper

import com.khs.movies.cache.db.entity.GenreEntity
import com.khs.movies.domain.mapper.BiMapper
import com.khs.movies.domain.model.Genre
import javax.inject.Inject

class GenreEntityMapper @Inject constructor() : BiMapper<GenreEntity, Genre> {

    override fun map(item: GenreEntity): Genre = with(item) {
        Genre(id, name)
    }

    override fun reverseMap(item: Genre): GenreEntity = with(item) {
        GenreEntity(id, name)
    }
}