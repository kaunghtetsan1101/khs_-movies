package com.khs.movies.data.repoimpl

import androidx.paging.PagingData
import com.khs.movies.data.datasource.person.PersonCacheDataSource
import com.khs.movies.data.datasource.person.PersonNetworkDataSource
import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.repository.PersonRepository
import com.khs.movies.domain.usecase.person.GetNetworkPopularPersons
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(
    private val cacheDataSource: PersonCacheDataSource,
    private val networkDataSource: PersonNetworkDataSource
) : PersonRepository {

    override suspend fun getNetworkPopularPersons(page: Int): List<ListingPerson> =
        networkDataSource.getPopularPersons(page)

    override fun getPopularPersons(getNetworkPopularPersons: GetNetworkPopularPersons): Flow<PagingData<ListingPerson>> =
        cacheDataSource.getPopularPersons(getNetworkPopularPersons)

    override suspend fun updateCachePerson(person: Person) {
        cacheDataSource.updatePerson(person)
    }

    override suspend fun getCachePerson(personId: Int): Person =
        cacheDataSource.getPerson(personId)

    override suspend fun getNetworkPerson(personId: Int): Person =
        networkDataSource.getPerson(personId)
}