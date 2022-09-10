package com.khs.movies.network.mapper.movie

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.movie.Movie
import com.khs.movies.network.mapper.GenreJsonMapper
import com.khs.movies.network.response.movie.MovieJson
import javax.inject.Inject

class MovieJsonMapper @Inject constructor(
    private val genreJsonMapper: GenreJsonMapper
) : UniMapper<MovieJson, Movie> {

    override fun map(item: MovieJson): Movie = with(item) {
        Movie(
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
            voteAverage = vote_average,
            genres = genres.map(genreJsonMapper::map),
            keywords = null,
            videos = null,
            reviews = null
        )
    }
}