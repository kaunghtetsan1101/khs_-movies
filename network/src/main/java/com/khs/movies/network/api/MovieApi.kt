package com.khs.movies.network.api

import com.khs.movies.network.response.keyword.GetKeywordResponse
import com.khs.movies.network.response.movie.MovieJson
import com.khs.movies.network.response.review.GetReviewResponse
import com.khs.movies.network.response.video.GetVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId: Int): MovieJson

    @GET("movie/{movie_id}/keywords")
    suspend fun getKeywords(@Path("movie_id") movieId: Int): GetKeywordResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getVideos(@Path("movie_id") movieId: Int): GetVideoResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(@Path("movie_id") id: Int): GetReviewResponse
}