package com.khs.movies.feature.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.khs.movies.domain.usecase.person.GetNetworkPopularPersons
import com.khs.movies.domain.usecase.person.GetPopularPersons
import com.khs.movies.feature.person.model.PopularPersonItemUiState
import com.khs.movies.feature.person.model.PopularPersonUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularPersonViewModel @Inject constructor(
    private val getNetworkPopularPersons: GetNetworkPopularPersons,
    private val getPopularPersons: GetPopularPersons
) : ViewModel() {

    private val _uiState = MutableStateFlow(PopularPersonUiState())
    val uiState: StateFlow<PopularPersonUiState> = _uiState.asStateFlow()

    fun fetchPersons() {
        viewModelScope.launch {
            if (_uiState.value.isEmpty)
                getPopularPersons(getNetworkPopularPersons)
                    .cachedIn(this)
                    .map { pagingData ->
                        pagingData.map {
                            PopularPersonItemUiState(
                                id = it.id,
                                name = it.name,
                                profilePath = it.profilePath
                            )
                        }
                    }
                    .collectLatest {
                        _uiState.update { currentState ->
                            currentState.copy(
                                personPage = it
                            )
                        }
                    }
        }
    }
}