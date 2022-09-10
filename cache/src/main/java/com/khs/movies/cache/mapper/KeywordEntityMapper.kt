package com.khs.movies.cache.mapper

import com.khs.movies.cache.db.entity.KeywordEntity
import com.khs.movies.domain.mapper.BiMapper
import com.khs.movies.domain.model.Keyword
import javax.inject.Inject

class KeywordEntityMapper @Inject constructor() : BiMapper<KeywordEntity, Keyword> {

    override fun map(item: KeywordEntity): Keyword = with(item) {
        Keyword(id, name)
    }

    override fun reverseMap(item: Keyword): KeywordEntity = with(item) {
        KeywordEntity(id, name)
    }
}