package com.khs.movies.domain.usecase.person

import com.khs.movies.domain.repository.PersonRepository
import javax.inject.Inject

class GetNetworkPopularPersons @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(page: Int) =
        personRepository.getNetworkPopularPersons(page)
}