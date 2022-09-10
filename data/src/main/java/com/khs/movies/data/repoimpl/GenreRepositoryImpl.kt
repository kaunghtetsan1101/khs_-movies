package com.khs.movies.data.repoimpl

import com.khs.movies.data.datasource.genre.GenreCacheDataSource
import com.khs.movies.domain.model.Genre
import com.khs.movies.domain.repository.GenreRepository
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val cacheDataSource: GenreCacheDataSource
) : GenreRepository {

    override suspend fun saveCacheGenresOfMovie(movieId: Int, genres: List<Genre>) {
        cacheDataSource.saveGenresOfMovie(movieId, genres)
    }
}