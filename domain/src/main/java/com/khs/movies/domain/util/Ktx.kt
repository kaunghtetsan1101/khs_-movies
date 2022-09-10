package com.khs.movies.domain.util

fun <T, R> T.mapp(transform: (T) -> R): R = transform(this)