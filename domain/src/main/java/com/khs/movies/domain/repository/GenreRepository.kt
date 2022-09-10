package com.khs.movies.domain.repository

import com.khs.movies.domain.model.Genre

interface GenreRepository {

    suspend fun saveCacheGenresOfMovie(movieId: Int, genres: List<Genre>)
}