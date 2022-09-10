package com.khs.movies.di

import com.khs.movies.appbase.di.BaseAppModule
import com.khs.movies.domain.exception.ExceptionToStringMapper
import com.khs.movies.exception.ExceptionToStringMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [BaseAppModule::class]
)
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun exceptionToStringMapper(exceptionToStringMapper: ExceptionToStringMapperImpl): ExceptionToStringMapper
}