package com.khs.movies.data.repoimpl

import com.khs.movies.data.datasource.keyword.KeywordCacheDataSource
import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.repository.KeywordRepository
import javax.inject.Inject

class KeywordRepositoryImpl @Inject constructor(
    private val cacheDataSource: KeywordCacheDataSource
) : KeywordRepository {

    override suspend fun saveCacheKeywordsOfMovie(movieId: Int, keywords: List<Keyword>) {
        cacheDataSource.saveKeywordsOfMovie(movieId, keywords)
    }
}