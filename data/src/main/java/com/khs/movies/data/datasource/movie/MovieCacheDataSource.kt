package com.khs.movies.data.datasource.movie

import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.model.movie.Movie

interface MovieCacheDataSource {

    suspend fun saveDiscoverMovies(movies: List<DiscoverMovie>)

    suspend fun updateMovie(movie: Movie)

    suspend fun getMovie(movieId: Int): Movie

    suspend fun deleteAllMovies()
}