package com.khs.movies.cache.db.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.khs.movies.cache.db.entity.movie.GenreMovieLinkEntity

@Dao
interface GenreMovieLinkDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenreMovieLink(vararg genreMovieLink: GenreMovieLinkEntity)
}