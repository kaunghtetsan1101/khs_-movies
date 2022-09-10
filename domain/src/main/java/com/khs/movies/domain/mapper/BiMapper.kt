package com.khs.movies.domain.mapper

interface BiMapper<From, To> {

    fun map(item: From): To

    fun reverseMap(item: To): From

}