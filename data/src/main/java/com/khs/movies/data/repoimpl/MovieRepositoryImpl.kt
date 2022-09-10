package com.khs.movies.data.repoimpl

import com.khs.movies.data.datasource.movie.MovieCacheDataSource
import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.model.movie.Movie
import com.khs.movies.domain.repository.MovieRepository
import com.khs.movies.data.datasource.movie.MovieNetworkDataSource
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val cacheDataSource: MovieCacheDataSource,
    private val networkDataSource: MovieNetworkDataSource
) : MovieRepository {

    override suspend fun saveCacheDiscoverMovies(movies: List<DiscoverMovie>) {
        cacheDataSource.saveDiscoverMovies(movies)
    }

    override suspend fun updateCacheMovie(movie: Movie) {
        cacheDataSource.updateMovie(movie)
    }

    override suspend fun getCacheMovie(movieId: Int): Movie =
        cacheDataSource.getMovie(movieId)

    override suspend fun getNetworkMovie(movieId: Int): Movie =
        networkDataSource.getMovie(movieId)

    override suspend fun getNetworkVideosOfMovie(movieId: Int): List<Video> =
        networkDataSource.getVideosOfMovie(movieId)

    override suspend fun getNetworkKeywordsOfMovie(movieId: Int): List<Keyword> =
        networkDataSource.getKeywordsOfMovie(movieId)

    override suspend fun getNetworkReviewsOfMovie(movieId: Int): List<Review> =
        networkDataSource.getReviewsOfMovie(movieId)

    override suspend fun deleteAllCacheMovies() {
        cacheDataSource.deleteAllMovies()
    }
}