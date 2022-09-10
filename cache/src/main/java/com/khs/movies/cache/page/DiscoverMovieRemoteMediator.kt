package com.khs.movies.cache.page

import androidx.room.withTransaction
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.db.entity.RemoteKeyEntity
import com.khs.movies.cache.db.entity.movie.MovieEntity
import com.khs.movies.cache.mapper.movie.DiscoverMovieEntityMapper
import com.khs.movies.data.STARTING_PAGE_INDEX
import com.khs.movies.data.datasource.BaseRemoteMediator
import com.khs.movies.domain.usecase.discover.GetNetworkDiscoverMovies

class DiscoverMovieRemoteMediator(
    label: String,
    private val query : String?,
    private val db: MovieAppDb,
    private val getNetworkDiscoverMovies: GetNetworkDiscoverMovies,
    private val discoverMovieEntityMapper: DiscoverMovieEntityMapper
) : BaseRemoteMediator<Int, MovieEntity>(label) {

    private val movieDao = db.movieDao()
    private val remoteKeyDao = db.remoteKeyDao()

    override suspend fun getNextPageKey(): Int? =
        remoteKeyDao.getRemoteKeyByLabel(label).nextPageKey

    override suspend fun executeNetworkCall(
        loadKey: Int?,
        pageSize: Int
    ): Pair<List<MovieEntity>, Int?> {
        val page = loadKey ?: STARTING_PAGE_INDEX
        return (getNetworkDiscoverMovies(query,page)
            .map(discoverMovieEntityMapper::reverseMap)) to (page + 1)
    }

    override suspend fun saveInCache(nextPageKey: Int?, items: List<MovieEntity>) {
        db.withTransaction {
            remoteKeyDao.insert(RemoteKeyEntity(label, nextPageKey))
            movieDao.insertMovie(*items.toTypedArray())
        }
    }

    override suspend fun reviseInCache(nextPageKey: Int?, items: List<MovieEntity>) {
        db.withTransaction {
            movieDao.deleteAll()
            remoteKeyDao.deleteByLabel(label)
            remoteKeyDao.insert(RemoteKeyEntity(label, nextPageKey))
            movieDao.insertMovie(*items.toTypedArray())
        }
    }
}