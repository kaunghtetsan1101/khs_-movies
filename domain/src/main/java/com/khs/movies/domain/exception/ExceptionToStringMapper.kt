package com.khs.movies.domain.exception

import com.khs.movies.domain.mapper.UniMapper

interface ExceptionToStringMapper : UniMapper<Throwable, String>