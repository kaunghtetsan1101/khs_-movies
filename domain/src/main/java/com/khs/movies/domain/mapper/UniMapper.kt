package com.khs.movies.domain.mapper

interface UniMapper<From, To> {

    fun map(item: From): To
}