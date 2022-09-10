package com.khs.movies.network.api

import com.khs.movies.network.response.movie.GetDiscoverMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET("discover/movie?language=en&sort_by=popularity.desc")
    suspend fun getDiscoverMovies(@Query("page") page: Int): GetDiscoverMovieResponse

    @GET("search/movie?language=en")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String,
    ): GetDiscoverMovieResponse
}