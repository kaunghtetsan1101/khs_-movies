package com.khs.movies.network.mapper


import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.Keyword
import com.khs.movies.network.response.keyword.KeywordJson
import javax.inject.Inject

class KeywordJsonMapper @Inject constructor() : UniMapper<KeywordJson, Keyword> {

    override fun map(item: KeywordJson): Keyword = with(item) {
        Keyword(id, name)
    }
}