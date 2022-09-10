package com.khs.movies.domain.interactor

import com.khs.movies.domain.usecase.person.GetCachePerson
import com.khs.movies.domain.usecase.person.GetNetworkPerson
import com.khs.movies.domain.usecase.person.UpdateCachePerson
import javax.inject.Inject

class PersonDetailInteractor @Inject constructor(
    val updateCachePerson: UpdateCachePerson,
    val getCachePerson: GetCachePerson,
    val getNetworkPerson: GetNetworkPerson
)