package com.khs.movies.cache.mapper.movie

import com.khs.movies.cache.db.pojo.MovieEntityOrm
import com.khs.movies.cache.di.CacheMoshi
import com.khs.movies.cache.mapper.GenreEntityMapper
import com.khs.movies.cache.mapper.KeywordEntityMapper
import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video
import com.khs.movies.domain.model.movie.Movie
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class MovieEntityOrmMapper @Inject constructor(
    private val genreEntityMapper: GenreEntityMapper,
    private val keywordEntityMapper: KeywordEntityMapper,
    @CacheMoshi private val moshi: Moshi
) : UniMapper<MovieEntityOrm, Movie> {

    override fun map(item: MovieEntityOrm): Movie = with(item.movie) {
        Movie(
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
            genres = item.genres.map(genreEntityMapper::map),
            keywords = item.keywords.map(keywordEntityMapper::map),
            videos = videosJsonStr?.let {
                moshi.adapter<List<Video>>(
                    Types.newParameterizedType(
                        List::class.java,
                        Video::class.java
                    )
                )
                    .fromJson(it)
            },
            reviews = reviewsJsonStr?.let {
                moshi.adapter<List<Review>>(
                    Types.newParameterizedType(
                        List::class.java,
                        Review::class.java
                    )
                )
                    .fromJson(it)
            }
        )
    }
}