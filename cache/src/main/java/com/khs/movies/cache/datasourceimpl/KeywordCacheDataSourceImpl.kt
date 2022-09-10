package com.khs.movies.cache.datasourceimpl

import com.khs.movies.data.datasource.keyword.KeywordCacheDataSource
import com.khs.movies.domain.model.Keyword
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.db.entity.movie.KeywordMovieLinkEntity
import com.khs.movies.cache.mapper.KeywordEntityMapper
import javax.inject.Inject

class KeywordCacheDataSourceImpl @Inject constructor(
    private val db: MovieAppDb,
    private val keywordEntityMapper: KeywordEntityMapper
) : KeywordCacheDataSource {

    override suspend fun saveKeywordsOfMovie(movieId: Int, keywords: List<Keyword>) {
        with(db) {
            keywordDao().insertKeyword(
                *keywords.map(keywordEntityMapper::reverseMap).toTypedArray()
            )
            keywordMovieLinkDao().insertKeywordMovieLink(
                *keywords.map { KeywordMovieLinkEntity(it.id, movieId) }
                    .toTypedArray()
            )
        }
    }
}