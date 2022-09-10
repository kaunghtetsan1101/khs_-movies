package com.khs.movies.feature.persondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khs.movies.appbase.helper.UserMessage
import com.khs.movies.domain.exception.ExceptionToStringMapper
import com.khs.movies.domain.interactor.PersonDetailInteractor
import com.khs.movies.domain.util.mapp
import com.khs.movies.feature.UserMessageIdGenerator
import com.khs.movies.feature.persondetail.model.PersonDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    private val personDetailInteractor: PersonDetailInteractor,
    private val exceptionToStringMapper: ExceptionToStringMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonDetailUiState(isLoading = true))
    val uiState: StateFlow<PersonDetailUiState> = _uiState.asStateFlow()

    init {
        val connectedState = _uiState
            .filter { it.navPersonId != null && it.isConnected }
        val notConnectedState = _uiState
            .filter { it.navPersonId != null && !it.isConnected }

        // Get Network Person
        connectedState.map {
            personDetailInteractor.getNetworkPerson(it.navPersonId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    person = it
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Update Network Person In Cache
        connectedState.filter {
            it.person != null
        }.onEach {
            personDetailInteractor.updateCachePerson(it.person!!)
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)

        // Get Cache Movie
        notConnectedState.map {
            personDetailInteractor.getCachePerson(it.navPersonId!!)
        }.onEach {
            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    person = it
                )
            }
        }.catch {
            onError(it)
        }.launchIn(viewModelScope)
    }

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

    fun setNavPersonIdAndConnectionStatus(navPersonId: Int, isConnected: Boolean) {
        _uiState.update { currentUiState ->
            currentUiState.copy(
                navPersonId = navPersonId,
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