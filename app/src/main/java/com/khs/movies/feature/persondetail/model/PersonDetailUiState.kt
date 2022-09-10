package com.khs.movies.feature.persondetail.model

import com.khs.movies.appbase.helper.UserMessage
import com.khs.movies.domain.model.person.Person

data class PersonDetailUiState(
    val isConnected: Boolean = false,
    val navPersonId: Int? = null,
    val isLoading: Boolean = false,
    val person: Person? = null,
    val userMessages: List<UserMessage> = listOf()
)