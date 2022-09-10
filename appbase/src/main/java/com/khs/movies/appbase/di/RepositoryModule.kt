package com.khs.movies.appbase.di

import com.khs.movies.cache.di.CacheModule
import com.khs.movies.data.repoimpl.*
import com.khs.movies.domain.repository.*
import com.khs.movies.network.di.NetworkModule
import dagger.Binds
import dagger.Module

@Module(includes = [CacheModule::class, NetworkModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun genreRepository(genreRepositoryImpl: GenreRepositoryImpl): GenreRepository

    @Binds
    abstract fun keywordRepository(keywordRepositoryImpl: KeywordRepositoryImpl): KeywordRepository

    @Binds
    abstract fun discoverRepository(discoverRepositoryImpl: DiscoverRepositoryImpl): DiscoverRepository

    @Binds
    abstract fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository

    @Binds
    abstract fun personRepository(personRepositoryImpl: PersonRepositoryImpl): PersonRepository
}