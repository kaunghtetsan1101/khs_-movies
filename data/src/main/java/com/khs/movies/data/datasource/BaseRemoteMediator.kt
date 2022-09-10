package com.khs.movies.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<Key : Any, Value : Any>(
    val label: String
) : RemoteMediator<Key, Value>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Key, Value>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = getNextPageKey()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey
                }
            }
            val result = executeNetworkCall(
                loadKey = loadKey,
                pageSize = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            )
            if (loadType == LoadType.REFRESH)
                reviseInCache(
                    nextPageKey = result.second,
                    items = result.first
                )
            else saveInCache(
                nextPageKey = result.second,
                items = result.first
            )
            return MediatorResult.Success(endOfPaginationReached = result.first.isEmpty())
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            return MediatorResult.Error(throwable)
        }
    }

    abstract suspend fun getNextPageKey(): Key?

    abstract suspend fun executeNetworkCall(
        loadKey: Key?,
        pageSize: Int
    ): Pair<List<Value>, Key?>

    abstract suspend fun saveInCache(
        nextPageKey: Key?,
        items: List<Value>
    )

    abstract suspend fun reviseInCache(
        nextPageKey: Key?,
        items: List<Value>
    )
}