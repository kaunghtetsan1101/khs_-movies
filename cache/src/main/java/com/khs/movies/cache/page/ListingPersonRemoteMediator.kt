package com.khs.movies.cache.page

import androidx.room.withTransaction
import com.khs.movies.data.STARTING_PAGE_INDEX
import com.khs.movies.data.datasource.BaseRemoteMediator
import com.khs.movies.domain.usecase.person.GetNetworkPopularPersons
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.db.entity.RemoteKeyEntity
import com.khs.movies.cache.db.entity.person.PersonEntity
import com.khs.movies.cache.mapper.person.ListingPersonEntityMapper

class ListingPersonRemoteMediator(
    label: String,
    private val db: MovieAppDb,
    private val getNetworkPopularPersons: GetNetworkPopularPersons,
    private val listingPersonEntityMapper: ListingPersonEntityMapper
) : BaseRemoteMediator<Int, PersonEntity>(label) {

    private val personDao = db.personDao()
    private val remoteKeyDao = db.remoteKeyDao()

    override suspend fun getNextPageKey(): Int? =
        remoteKeyDao.getRemoteKeyByLabel(label).nextPageKey

    override suspend fun executeNetworkCall(
        loadKey: Int?,
        pageSize: Int
    ): Pair<List<PersonEntity>, Int?> {
        val page = loadKey ?: STARTING_PAGE_INDEX
        return (getNetworkPopularPersons(page)
            .map(listingPersonEntityMapper::reverseMap)) to (page + 1)
    }

    override suspend fun saveInCache(nextPageKey: Int?, items: List<PersonEntity>) {
        db.withTransaction {
            remoteKeyDao.insert(RemoteKeyEntity(label, nextPageKey))
            personDao.insertPerson(*items.toTypedArray())
        }
    }

    override suspend fun reviseInCache(nextPageKey: Int?, items: List<PersonEntity>) {
        db.withTransaction {
            personDao.deleteAll()
            remoteKeyDao.deleteByLabel(label)
            remoteKeyDao.insert(RemoteKeyEntity(label, nextPageKey))
            personDao.insertPerson(*items.toTypedArray())
        }
    }
}