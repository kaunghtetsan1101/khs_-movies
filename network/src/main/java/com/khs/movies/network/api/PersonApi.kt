package com.khs.movies.network.api

import com.khs.movies.network.response.person.GetPopularPersonsResponse
import com.khs.movies.network.response.person.PersonJson
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonApi {

    @GET("person/popular?language=en")
    suspend fun getPopularPersons(@Query("page") page: Int): GetPopularPersonsResponse

    @GET("person/{person_id}")
    suspend fun getPersonById(@Path("person_id") id: Int): PersonJson
}