package com.khs.movies.cache.db.dao.movie

import androidx.paging.PagingSource
import androidx.room.*
import com.khs.movies.cache.db.entity.movie.MovieEntity
import com.khs.movies.cache.db.pojo.MovieEntityOrm

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movie: MovieEntity)

    @Query("SELECT * FROM MOVIE ORDER BY POPULARITY DESC")
    fun getMovies(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM MOVIE WHERE TITLE= :query ORDER BY POPULARITY DESC")
    fun searchMovies(query :String): PagingSource<Int, MovieEntity>

    @Transaction
    @Query("SELECT * FROM MOVIE WHERE ID = :id")
    suspend fun getMovieById(id: Int): MovieEntityOrm

    @Query("DELETE FROM MOVIE")
    suspend fun deleteAll()
}