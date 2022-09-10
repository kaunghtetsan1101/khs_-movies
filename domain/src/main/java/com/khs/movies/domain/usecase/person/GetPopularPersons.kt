package com.khs.movies.domain.usecase.person

import com.khs.movies.domain.repository.PersonRepository
import javax.inject.Inject

class GetPopularPersons @Inject constructor(
    private val personRepository: PersonRepository
) {
    operator fun invoke(getNetworkPopularPersons: GetNetworkPopularPersons) =
        personRepository.getPopularPersons(getNetworkPopularPersons)
}