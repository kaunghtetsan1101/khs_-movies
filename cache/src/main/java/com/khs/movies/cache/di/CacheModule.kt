package com.khs.movies.cache.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.khs.movies.cache.datasourceimpl.*
import com.khs.movies.cache.db.DB_NAME
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.data.datasource.discover.DiscoverCacheDataSource
import com.khs.movies.data.datasource.genre.GenreCacheDataSource
import com.khs.movies.data.datasource.keyword.KeywordCacheDataSource
import com.khs.movies.data.datasource.movie.MovieCacheDataSource
import com.khs.movies.data.datasource.person.PersonCacheDataSource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CacheModule.Providers::class])
abstract class CacheModule {

    @Binds
    abstract fun keywordCacheDataSource(keywordCacheDataSourceImpl: KeywordCacheDataSourceImpl): KeywordCacheDataSource

    @Binds
    abstract fun genreCacheDataSource(genreCacheDataSourceImpl: GenreCacheDataSourceImpl): GenreCacheDataSource

    @Binds
    abstract fun movieCacheDataSource(movieCacheDataSourceImpl: MovieCacheDataSourceImpl): MovieCacheDataSource

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    abstract fun discoverCacheDataSource(discoverCacheDataSourceImpl: DiscoverCacheDataSourceImpl): DiscoverCacheDataSource

    @OptIn(ExperimentalPagingApi::class)
    @Binds
    abstract fun personCacheDataSource(personCacheDataSourceImpl: PersonCacheDataSourceImpl): PersonCacheDataSource

    @Module
    internal object Providers {

        @Singleton
        @Provides
        fun provideAppDb(app: Application): MovieAppDb = Room
            .databaseBuilder(
                app,
                MovieAppDb::class.java,
                DB_NAME
            )
            .build()

        @Singleton
        @CacheMoshi
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

}