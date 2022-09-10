package com.khs.movies.domain.usecase.person

import com.khs.movies.domain.repository.PersonRepository
import javax.inject.Inject

class GetNetworkPerson @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(personId: Int) =
        personRepository.getNetworkPerson(personId)
}