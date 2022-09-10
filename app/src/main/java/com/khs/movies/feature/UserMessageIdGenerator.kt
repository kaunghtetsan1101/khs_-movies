package com.khs.movies.feature

import java.util.*

object UserMessageIdGenerator {
    val newId: Long
        get() = UUID.randomUUID().mostSignificantBits
}