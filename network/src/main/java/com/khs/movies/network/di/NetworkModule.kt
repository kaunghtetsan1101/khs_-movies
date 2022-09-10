package com.khs.movies.network.di

import com.khs.movies.data.datasource.discover.DiscoverNetworkDataSource
import com.khs.movies.data.datasource.movie.MovieNetworkDataSource
import com.khs.movies.data.datasource.person.PersonNetworkDataSource
import com.khs.movies.network.BuildConfig
import com.khs.movies.network.api.Api
import com.khs.movies.network.datasourceimpl.DiscoverNetworkDataSourceImpl
import com.khs.movies.network.datasourceimpl.MovieNetworkDataSourceImpl
import com.khs.movies.network.datasourceimpl.PersonNetworkDataSourceImpl
import com.khs.movies.network.interceptor.AuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, NetworkModule.Provider::class])
abstract class NetworkModule {

    @Binds
    abstract fun movieNetworkDataSource(movieNetworkDataSourceImpl: MovieNetworkDataSourceImpl): MovieNetworkDataSource

    @Binds
    abstract fun discoverNetworkDataSource(discoverNetworkDataSourceImpl: DiscoverNetworkDataSourceImpl): DiscoverNetworkDataSource

    @Binds
    abstract fun personNetworkDataSource(personNetworkDataSourceImpl: PersonNetworkDataSourceImpl): PersonNetworkDataSource

    @Module
    object Provider {

        @Provides
        @Singleton
        fun okHttpClient(
            authInterceptor: AuthInterceptor,
        ): OkHttpClient {

            val okHttpClientBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
            }

            return okHttpClientBuilder
                .addInterceptor(authInterceptor)
                .connectTimeout(Api.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Api.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Api.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS)
                .build()
        }

        @Singleton
        @NetworkMoshi
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder()
//            .add(JsonTypeAdapter())
            .addLast(KotlinJsonAdapterFactory())
            .build()

    }

}
