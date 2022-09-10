package com.khs.movies.cache.db.pojo

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.khs.movies.cache.db.entity.GenreEntity
import com.khs.movies.cache.db.entity.KeywordEntity
import com.khs.movies.cache.db.entity.movie.GenreMovieLinkEntity
import com.khs.movies.cache.db.entity.movie.KeywordMovieLinkEntity
import com.khs.movies.cache.db.entity.movie.MovieEntity

data class MovieEntityOrm(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "ID",
        entityColumn = "ID",
        associateBy = Junction(
            value = GenreMovieLinkEntity::class,
            parentColumn = "MOVIE_ID",
            entityColumn = "GENRE_ID"
        )
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "ID",
        entityColumn = "ID",
        associateBy = Junction(
            value = KeywordMovieLinkEntity::class,
            parentColumn = "MOVIE_ID",
            entityColumn = "KEYWORD_ID"
        )
    )
    val keywords: List<KeywordEntity>
)
