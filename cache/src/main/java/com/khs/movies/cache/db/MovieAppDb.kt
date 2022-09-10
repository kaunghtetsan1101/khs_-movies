package com.khs.movies.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khs.movies.cache.db.dao.GenreDao
import com.khs.movies.cache.db.dao.KeywordDao
import com.khs.movies.cache.db.dao.RemoteKeyDao
import com.khs.movies.cache.db.dao.movie.GenreMovieLinkDao
import com.khs.movies.cache.db.dao.movie.KeywordMovieLinkDao
import com.khs.movies.cache.db.dao.movie.MovieDao
import com.khs.movies.cache.db.dao.person.PersonDao
import com.khs.movies.cache.db.entity.GenreEntity
import com.khs.movies.cache.db.entity.KeywordEntity
import com.khs.movies.cache.db.entity.RemoteKeyEntity
import com.khs.movies.cache.db.entity.movie.GenreMovieLinkEntity
import com.khs.movies.cache.db.entity.movie.KeywordMovieLinkEntity
import com.khs.movies.cache.db.entity.movie.MovieEntity
import com.khs.movies.cache.db.entity.person.PersonEntity

@Database(
    entities = [
        GenreEntity::class, KeywordEntity::class, MovieEntity::class,
        GenreMovieLinkEntity::class, KeywordMovieLinkEntity::class, RemoteKeyEntity::class,
        PersonEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieAppDb : RoomDatabase() {

    abstract fun genreDao(): GenreDao

    abstract fun keywordDao(): KeywordDao

    abstract fun movieDao(): MovieDao

    abstract fun genreMovieLinkDao(): GenreMovieLinkDao

    abstract fun keywordMovieLinkDao(): KeywordMovieLinkDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun personDao(): PersonDao
}