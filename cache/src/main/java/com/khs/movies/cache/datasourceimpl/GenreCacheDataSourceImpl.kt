package com.khs.movies.cache.datasourceimpl

import com.khs.movies.data.datasource.genre.GenreCacheDataSource
import com.khs.movies.domain.model.Genre
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.db.entity.movie.GenreMovieLinkEntity
import com.khs.movies.cache.mapper.GenreEntityMapper

import javax.inject.Inject

class GenreCacheDataSourceImpl @Inject constructor(
    private val db: MovieAppDb,
    private val genreEntityMapper: GenreEntityMapper
) : GenreCacheDataSource {

    override suspend fun saveGenresOfMovie(movieId: Int, genres: List<Genre>) {
        with(db) {
            genreDao().insertGenre(*genres.map(genreEntityMapper::reverseMap).toTypedArray())
            genreMovieLinkDao().insertGenreMovieLink(
                *genres.map { GenreMovieLinkEntity(it.id, movieId) }
                    .toTypedArray()
            )
        }
    }
}