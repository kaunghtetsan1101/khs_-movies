package com.khs.movies.domain.usecase.person

import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.repository.PersonRepository
import javax.inject.Inject

class UpdateCachePerson @Inject constructor(
    private val personRepository: PersonRepository
) {
    suspend operator fun invoke(person: Person) =
        personRepository.updateCachePerson(person)
}