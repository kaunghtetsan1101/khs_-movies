package com.khs.movies.feature.moviedetail.model

import com.khs.movies.appbase.helper.UserMessage
import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.model.movie.Movie
import com.khs.movies.feature.common.model.KeywordItemUiState
import com.khs.movies.feature.common.model.ReviewItemUiState
import com.khs.movies.feature.common.model.VideoItemUiState

data class MovieDetailUiState(
    val isConnected: Boolean = false,
    val navMovieId: Int? = null,
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val networkKeywords: List<Keyword>? = null,
    val keywords: List<KeywordItemUiState> = listOf(),
    val networkVideos: List<Video>? = null,
    val videos: List<VideoItemUiState> = listOf(),
    val networkReviews: List<Review>? = null,
    val reviews: List<ReviewItemUiState> = listOf(),
    val userMessages: List<UserMessage> = listOf(),
) {
    val isAllNetworkDataFetched: Boolean
        get() = movie != null && networkKeywords != null
                && networkVideos != null && networkReviews != null
}