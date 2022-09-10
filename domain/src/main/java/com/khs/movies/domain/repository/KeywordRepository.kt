package com.khs.movies.domain.repository

import com.khs.movies.domain.model.Keyword

interface KeywordRepository {

    suspend fun saveCacheKeywordsOfMovie(movieId: Int, keywords: List<Keyword>)
}