package com.khs.movies.domain.repository

import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.model.movie.Movie

interface MovieRepository {

    suspend fun saveCacheDiscoverMovies(movies: List<DiscoverMovie>)

    suspend fun updateCacheMovie(movie: Movie)

    suspend fun getCacheMovie(movieId: Int): Movie

    suspend fun getNetworkMovie(movieId: Int): Movie

    suspend fun getNetworkVideosOfMovie(movieId: Int): List<Video>

    suspend fun getNetworkKeywordsOfMovie(movieId: Int): List<Keyword>

    suspend fun getNetworkReviewsOfMovie(movieId: Int): List<Review>

    suspend fun deleteAllCacheMovies()
}