package com.khs.movies.data.datasource.person

import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.domain.model.person.Person


interface PersonNetworkDataSource {

    suspend fun getPopularPersons(page: Int): List<ListingPerson>

    suspend fun getPerson(personId: Int): Person
}