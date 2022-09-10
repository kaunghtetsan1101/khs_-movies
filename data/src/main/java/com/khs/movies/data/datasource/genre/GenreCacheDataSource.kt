package com.khs.movies.data.datasource.genre

import com.khs.movies.domain.model.Genre

interface GenreCacheDataSource {

    suspend fun saveGenresOfMovie(movieId: Int, genres: List<Genre>)
}