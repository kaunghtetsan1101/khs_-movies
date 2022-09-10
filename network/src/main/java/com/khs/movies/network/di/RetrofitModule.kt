package com.khs.movies.network.di

import com.khs.movies.network.api.DiscoverApi
import com.khs.movies.network.api.MovieApi
import com.khs.movies.network.api.PersonApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [RetrofitModule.Provider::class])
abstract class RetrofitModule {

    @Module
    object Provider {

        @Provides
        @Singleton
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            @NetworkMoshi moshi: Moshi,
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(com.khs.movies.network.api.Api.BASE_API_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

        @Singleton
        @Provides
        fun provideDiscoverApi(retrofit: Retrofit): DiscoverApi {
            return retrofit.create(DiscoverApi::class.java)
        }

        @Singleton
        @Provides
        fun provideMovieApi(retrofit: Retrofit): MovieApi {
            return retrofit.create(MovieApi::class.java)
        }

        @Singleton
        @Provides
        fun providePersonApi(retrofit: Retrofit): PersonApi {
            return retrofit.create(PersonApi::class.java)
        }
    }

}