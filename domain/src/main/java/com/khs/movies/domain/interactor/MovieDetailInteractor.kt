package com.khs.movies.domain.interactor

import com.khs.movies.domain.usecase.movie.*
import javax.inject.Inject

class MovieDetailInteractor @Inject constructor(
    val updateCacheMovie: UpdateCacheMovie,
    val getCacheMovie: GetCacheMovie,
    val getNetworkMovie: GetNetworkMovie,
    val getNetworkVideosOfMovie: GetNetworkVideosOfMovie,
    val getNetworkKeywordsOfMovie: GetNetworkKeywordsOfMovie,
    val getNetworkReviewsOfMovie: GetNetworkReviewsOfMovie
)