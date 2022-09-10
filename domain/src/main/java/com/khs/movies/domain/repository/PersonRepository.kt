package com.khs.movies.domain.repository

import androidx.paging.PagingData
import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.usecase.person.GetNetworkPopularPersons
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    suspend fun getNetworkPopularPersons(page: Int): List<ListingPerson>

    fun getPopularPersons(getNetworkPopularPersons: GetNetworkPopularPersons): Flow<PagingData<ListingPerson>>

    suspend fun updateCachePerson(person: Person)

    suspend fun getCachePerson(personId: Int): Person

    suspend fun getNetworkPerson(personId: Int): Person
}