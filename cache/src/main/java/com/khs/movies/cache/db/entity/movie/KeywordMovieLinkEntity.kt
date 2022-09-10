package com.khs.movies.cache.db.entity.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.khs.movies.cache.db.KEYWORD_MOVIE_LINK
import com.khs.movies.cache.db.entity.KeywordEntity

@Entity(
    tableName = KEYWORD_MOVIE_LINK,
    indices = [
        Index(value = arrayOf("KEYWORD_ID")),
        Index(value = arrayOf("MOVIE_ID")),
    ],
    primaryKeys = ["KEYWORD_ID", "MOVIE_ID"],
    foreignKeys = [
        ForeignKey(
            entity = KeywordEntity::class,
            parentColumns = ["ID"],
            childColumns = ["KEYWORD_ID"],
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
data class KeywordMovieLinkEntity(
    @ColumnInfo(name = "KEYWORD_ID")
    val keywordId: Int,
    @ColumnInfo(name = "MOVIE_ID")
    val movieId: Int
)