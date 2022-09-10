package com.khs.movies.network.datasourceimpl

import com.khs.movies.data.datasource.person.PersonNetworkDataSource
import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.util.mapp
import com.khs.movies.network.api.PersonApi
import com.khs.movies.network.mapper.person.ListingPersonJsonMapper
import com.khs.movies.network.mapper.person.PersonJsonMapper
import javax.inject.Inject

class PersonNetworkDataSourceImpl @Inject constructor(
    private val personApi: PersonApi,
    private val listingPersonJsonMapper: ListingPersonJsonMapper,
    private val personJsonMapper: PersonJsonMapper
) : PersonNetworkDataSource {

    override suspend fun getPopularPersons(page: Int): List<ListingPerson> =
        personApi.getPopularPersons(page).results
            .map(listingPersonJsonMapper::map)

    override suspend fun getPerson(personId: Int): Person =
        personApi.getPersonById(personId)
            .mapp(personJsonMapper::map)
}