package com.khs.movies.domain.model.movie

import com.khs.movies.domain.model.Genre
import com.khs.movies.domain.model.Keyword
import com.khs.movies.domain.model.Review
import com.khs.movies.domain.model.Video

data class Movie(
    val id: Int,
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String?,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val backdropPath: String?,
    val popularity: Float,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Float,
    val genres: List<Genre>,
    val keywords: List<Keyword>?,
    val videos: List<Video>?,
    val reviews: List<Review>?
)