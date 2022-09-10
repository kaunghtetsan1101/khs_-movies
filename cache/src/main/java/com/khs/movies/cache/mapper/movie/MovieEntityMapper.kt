package com.khs.movies.cache.mapper.movie

import com.khs.movies.cache.db.entity.movie.MovieEntity
import com.khs.movies.cache.di.CacheMoshi
import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.movie.Movie
import com.squareup.moshi.Moshi
import javax.inject.Inject

class MovieEntityMapper @Inject constructor(
    @CacheMoshi private val moshi: Moshi
) : UniMapper<Movie, MovieEntity> {

    override fun map(item: Movie): MovieEntity = with(item) {
        MovieEntity(
            id = id,
            posterPath = posterPath,
            adult = adult,
            overview = overview,
            releaseDate = releaseDate,
            originalTitle = originalTitle,
            originalLanguage = originalLanguage,
            title = title,
            backdropPath = backdropPath,
            popularity = popularity,
            voteCount = voteCount,
            video = video,
            voteAverage = voteAverage,
            videosJsonStr = videos?.let {
                moshi.adapter(List::class.java).toJson(it)
            },
            reviewsJsonStr = reviews?.let {
                moshi.adapter(List::class.java).toJson(it)
            }
        )
    }
}