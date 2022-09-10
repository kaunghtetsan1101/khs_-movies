package com.khs.movies.cache.datasourceimpl

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.paging.*
import com.khs.movies.data.DISCOVER_MOVIE_REMOTE_KEY_LABEL
import com.khs.movies.data.datasource.discover.DiscoverCacheDataSource
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.usecase.discover.GetNetworkDiscoverMovies
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.mapper.movie.DiscoverMovieEntityMapper
import com.khs.movies.cache.page.DiscoverMovieRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalPagingApi
class DiscoverCacheDataSourceImpl @Inject constructor(
    private val db: MovieAppDb,
    private val discoverMovieEntityMapper: DiscoverMovieEntityMapper
) : DiscoverCacheDataSource {

    override fun getDiscoverMovies(query : String?,getNetworkDiscoverMovies: GetNetworkDiscoverMovies): Flow<PagingData<DiscoverMovie>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            remoteMediator = DiscoverMovieRemoteMediator(
                query = query,
                label = DISCOVER_MOVIE_REMOTE_KEY_LABEL,
                db = db,
                getNetworkDiscoverMovies = getNetworkDiscoverMovies,
                discoverMovieEntityMapper = discoverMovieEntityMapper
            )
        ) {
            if (query.isNullOrEmpty()){
                db.movieDao().getMovies()
            } else {
                db.movieDao().searchMovies(query.trim())
            }
        }.flow.map { it.map(discoverMovieEntityMapper::map) }
}