package com.khs.movies.feature.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.movies.appbase.helper.UserMessage
import com.khs.movies.domain.exception.ExceptionToStringMapper
import com.khs.movies.domain.interactor.MovieDetailInteractor
import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.util.mapp
import com.khs.movies.feature.UserMessageIdGenerator
import com.khs.movies.feature.common.model.KeywordItemUiState
import com.khs.movies.feature.common.model.ReviewItemUiState
import com.khs.movies.feature.common.model.VideoItemUiState
import com.khs.movies.feature.moviedetail.model.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailInteractor: MovieDetailInteractor,
    private val exceptionToStringMapper: ExceptionToStringMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState(isLoading = true))
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        val connectedState = _uiState
            .filter { it.navMovieId != null && it.isConnected }
        val notConnectedState = _uiState
            .filter { it.navMovieId != null && !it.isConnected }

        // Get Network Movie
        connectedState.map {
            movieDetailInteractor.getNetworkMovie(it.navMovieId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    movie = it
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Get Network Keywords Of Movie
        connectedState.map {
            movieDetailInteractor.getNetworkKeywordsOfMovie(it.navMovieId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    networkKeywords = it,
                    keywords = it.map(this::map)
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Get Network Videos Of Movie
        connectedState.map {
            movieDetailInteractor.getNetworkVideosOfMovie(it.navMovieId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    networkVideos = it,
                    videos = it.map(this::map)
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Get Network Reviews Of Movie
        connectedState.map {
            movieDetailInteractor.getNetworkReviewsOfMovie(it.navMovieId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    networkReviews = it,
                    reviews = it.map(this::map)
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Update Network Movie In Cache
        connectedState.filter {
            it.isAllNetworkDataFetched
        }.onEach {
            movieDetailInteractor.updateCacheMovie(
                movie = it.movie!!.copy(
                    keywords = it.networkKeywords,
                    videos = it.networkVideos,
                    reviews = it.networkReviews
                )
            )
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Get Cache Movie
        notConnectedState.map {
            movieDetailInteractor.getCacheMovie(it.navMovieId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    movie = it,
                    keywords = it.keywords?.map(this::map) ?: emptyList(),
                    videos = it.videos?.map(this::map) ?: emptyList(),
                    reviews = it.reviews?.map(this::map) ?: emptyList()
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)
    }

    private fun map(keyword: Keyword): KeywordItemUiState =
        KeywordItemUiState(
            id = keyword.id,
            name = keyword.name
        )

    private fun map(video: Video): VideoItemUiState =
        VideoItemUiState(
            id = video.id,
            key = video.key
        )

    private fun map(review: Review): ReviewItemUiState =
        ReviewItemUiState(
            id = review.id,
            author = review.author,
            content = review.content
        )

    private fun onError(throwable: Throwable) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoading = false,
                userMessages = currentUiState.userMessages + UserMessage(
                    id = UserMessageIdGenerator.newId,
                    message = throwable.mapp(exceptionToStringMapper::map)
                )
            )
        }
    }

    fun retry() {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                isLoading = true
            )
        }
    }

    fun setNavMovieIdAndConnectionStatus(navMovieId: Int, isConnected: Boolean) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                navMovieId = navMovieId,
                isConnected = isConnected
            )
        }
    }

    fun userMessageShown(messageId: Long) {
        _uiState.update { currentUiState ->
            val messages = currentUiState.userMessages.filterNot { it.id == messageId }
            currentUiState.copy(userMessages = messages)
        }
    }
}