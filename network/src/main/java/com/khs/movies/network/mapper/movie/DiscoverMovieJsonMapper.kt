package com.khs.movies.network.mapper.movie

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.network.response.movie.DiscoverMovieJson
import javax.inject.Inject

class DiscoverMovieJsonMapper @Inject constructor() : UniMapper<DiscoverMovieJson, DiscoverMovie> {

    override fun map(item: DiscoverMovieJson): DiscoverMovie = with(item) {
        DiscoverMovie(
            id = id,
            posterPath = poster_path,
            adult = adult,
            overview = overview,
            releaseDate = release_date,
            originalTitle = original_title,
            originalLanguage = original_language,
            title = title,
            backdropPath = backdrop_path,
            popularity = popularity,
            voteCount = vote_count,
            video = video,
            voteAverage = vote_average
        )
    }
}