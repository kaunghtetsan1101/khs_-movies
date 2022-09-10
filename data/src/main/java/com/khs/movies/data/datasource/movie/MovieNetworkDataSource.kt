package com.khs.movies.data.datasource.movie

import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.model.movie.Movie


interface MovieNetworkDataSource {

    suspend fun getMovie(movieId: Int): Movie

    suspend fun getVideosOfMovie(movieId: Int): List<Video>

    suspend fun getKeywordsOfMovie(movieId: Int): List<Keyword>

    suspend fun getReviewsOfMovie(movieId: Int): List<Review>
}