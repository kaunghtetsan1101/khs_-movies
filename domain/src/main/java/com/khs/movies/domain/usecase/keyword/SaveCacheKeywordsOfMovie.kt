package com.khs.movies.domain.usecase.keyword

import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.repository.KeywordRepository
import javax.inject.Inject

class SaveCacheKeywordsOfMovie @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    suspend operator fun invoke(movieId: Int, keywords: List<Keyword>) =
        keywordRepository.saveCacheKeywordsOfMovie(movieId, keywords)
}