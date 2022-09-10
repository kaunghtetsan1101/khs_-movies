package com.khs.movies.cache.datasourceimpl

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.*
import com.khs.movies.data.LISTING_PERSON_REMOTE_KEY_LABEL
import com.khs.movies.data.datasource.person.PersonCacheDataSource
import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.usecase.person.GetNetworkPopularPersons
import com.khs.movies.domain.util.mapp
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.mapper.person.ListingPersonEntityMapper
import com.khs.movies.cache.mapper.person.PersonEntityMapper
import com.khs.movies.cache.page.ListingPersonRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class PersonCacheDataSourceImpl @Inject constructor(
    private val db: MovieAppDb,
    private val listingPersonEntityMapper: ListingPersonEntityMapper,
    private val personEntityMapper: PersonEntityMapper
) : PersonCacheDataSource {

    private val dao = db.personDao()

    override fun getPopularPersons(getNetworkPopularPersons: GetNetworkPopularPersons): Flow<PagingData<ListingPerson>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = ListingPersonRemoteMediator(
                label = LISTING_PERSON_REMOTE_KEY_LABEL,
                db = db,
                getNetworkPopularPersons = getNetworkPopularPersons,
                listingPersonEntityMapper = listingPersonEntityMapper
            )
        ) {
            dao.getPersons()
        }.flow.map { it.map(listingPersonEntityMapper::map) }

    override suspend fun updatePerson(person: Person) {
        dao.insertPerson(person.mapp(personEntityMapper::reverseMap))
    }

    override suspend fun getPerson(personId: Int): Person =
        dao.getPersonById(personId).mapp(personEntityMapper::map)
}