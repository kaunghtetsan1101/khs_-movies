package com.khs.movies.cache.db.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.khs.movies.cache.db.GENRE_MOVIE_LINK
import com.khs.movies.cache.db.entity.GenreEntity

@Entity(
    tableName = GENRE_MOVIE_LINK,
    indices = [
        Index(value = arrayOf("GENRE_ID")),
        Index(value = arrayOf("MOVIE_ID")),
    ],
    primaryKeys = ["GENRE_ID", "MOVIE_ID"],
    foreignKeys = [
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["ID"],
            childColumns = ["GENRE_ID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["ID"],
            childColumns = ["MOVIE_ID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GenreMovieLinkEntity(
    @ColumnInfo(name = "GENRE_ID")
    val genreId: Int,
    @ColumnInfo(name = "MOVIE_ID")
    val movieId: Int
)