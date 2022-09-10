package com.khs.movies.cache.mapper.movie

import com.khs.movies.domain.mapper.BiMapper
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.cache.db.entity.movie.MovieEntity
import javax.inject.Inject

class DiscoverMovieEntityMapper @Inject constructor() : BiMapper<MovieEntity, DiscoverMovie> {

    override fun map(item: MovieEntity): DiscoverMovie = with(item) {
        DiscoverMovie(
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
            voteAverage = voteAverage
        )
    }

    override fun reverseMap(item: DiscoverMovie): MovieEntity = with(item) {
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
            videosJsonStr = null,
            reviewsJsonStr = null
        )
    }
}