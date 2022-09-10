package com.khs.movies.network.datasourceimpl

import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.model.movie.Movie
import com.khs.movies.domain.util.mapp
import com.khs.movies.data.datasource.movie.MovieNetworkDataSource
import com.khs.movies.network.api.MovieApi
import com.khs.movies.network.mapper.KeywordJsonMapper
import com.khs.movies.network.mapper.ReviewJsonMapper
import com.khs.movies.network.mapper.VideoJsonMapper
import com.khs.movies.network.mapper.movie.MovieJsonMapper
import javax.inject.Inject

class MovieNetworkDataSourceImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieJsonMapper: MovieJsonMapper,
    private val videoJsonMapper: VideoJsonMapper,
    private val keywordJsonMapper: KeywordJsonMapper,
    private val reviewJsonMapper: ReviewJsonMapper
) : MovieNetworkDataSource {

    override suspend fun getMovie(movieId: Int): Movie =
        movieApi.getMovieById(movieId).mapp(movieJsonMapper::map)

    override suspend fun getVideosOfMovie(movieId: Int): List<Video> =
        movieApi.getVideos(movieId).results.map(videoJsonMapper::map)

    override suspend fun getKeywordsOfMovie(movieId: Int): List<Keyword> =
        movieApi.getKeywords(movieId).keywords.map(keywordJsonMapper::map)

    override suspend fun getReviewsOfMovie(movieId: Int): List<Review> =
        movieApi.getReviews(movieId).results.map(reviewJsonMapper::map)
}