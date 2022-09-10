package com.khs.movies.cache.db.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.khs.movies.cache.db.MOVIE

@Entity(tableName = MOVIE)
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "ID")
    val id: Int,
    @ColumnInfo(name = "POSTER_PATH")
    val posterPath: String?,
    @ColumnInfo(name = "ADULT")
    val adult: Boolean,
    @ColumnInfo(name = "OVERVIEW")
    val overview: String,
    @ColumnInfo(name = "RELEASE_DATE")
    val releaseDate: String?,
    @ColumnInfo(name = "ORIGINAL_TITLE")
    val originalTitle: String,
    @ColumnInfo(name = "ORIGINAL_LANGUAGE")
    val originalLanguage: String,
    @ColumnInfo(name = "TITLE")
    val title: String,
    @ColumnInfo(name = "BACK_DROP_PATH")
    val backdropPath: String?,
    @ColumnInfo(name = "POPULARITY")
    val popularity: Float,
    @ColumnInfo(name = "VOTE_COUNT")
    val voteCount: Int,
    @ColumnInfo(name = "VIDEO")
    val video: Boolean,
    @ColumnInfo(name = "VOTE_AVERAGE")
    val voteAverage: Float,
    @ColumnInfo(name = "VIDEOS")
    val videosJsonStr: String?,
    @ColumnInfo(name = "REVIEWS")
    val reviewsJsonStr: String?
)