package com.khs.movies.data.datasource.keyword

import com.khs.movies.domain.model.Keyword

interface KeywordCacheDataSource {

    suspend fun saveKeywordsOfMovie(movieId: Int, keywords: List<Keyword>)
}