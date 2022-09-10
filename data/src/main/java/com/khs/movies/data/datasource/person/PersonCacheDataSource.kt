package com.khs.movies.data.datasource.person

import androidx.paging.PagingData
import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.usecase.person.GetNetworkPopularPersons
import kotlinx.coroutines.flow.Flow

interface PersonCacheDataSource {

    fun getPopularPersons(getNetworkPopularPersons: GetNetworkPopularPersons): Flow<PagingData<ListingPerson>>

    suspend fun updatePerson(person: Person)

    suspend fun getPerson(personId: Int): Person
}