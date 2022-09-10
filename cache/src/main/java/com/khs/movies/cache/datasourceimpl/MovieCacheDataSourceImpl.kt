package com.khs.movies.cache.datasourceimpl

import androidx.room.withTransaction
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.domain.model.movie.Movie
import com.khs.movies.domain.usecase.genre.SaveCacheGenresOfMovie
import com.khs.movies.domain.usecase.keyword.SaveCacheKeywordsOfMovie
import com.khs.movies.domain.util.mapp
import com.khs.movies.cache.db.MovieAppDb
import com.khs.movies.cache.mapper.movie.DiscoverMovieEntityMapper
import com.khs.movies.cache.mapper.movie.MovieEntityMapper
import com.khs.movies.cache.mapper.movie.MovieEntityOrmMapper
import com.khs.movies.data.datasource.movie.MovieCacheDataSource
import javax.inject.Inject

class MovieCacheDataSourceImpl @Inject constructor(
    private val db: MovieAppDb,
    private val discoverMovieEntityMapper: DiscoverMovieEntityMapper,
    private val movieEntityMapper: MovieEntityMapper,
    private val saveCacheGenresOfMovie: SaveCacheGenresOfMovie,
    private val saveCacheKeywordsOfMovie: SaveCacheKeywordsOfMovie,
    private val movieEntityOrmMapper: MovieEntityOrmMapper
) : MovieCacheDataSource {

    private val dao = db.movieDao()

    override suspend fun saveDiscoverMovies(movies: List<DiscoverMovie>) {
        dao.insertMovie(*movies.map(discoverMovieEntityMapper::reverseMap).toTypedArray())
    }

    override suspend fun updateMovie(movie: Movie) {
        with(movie) {
            db.withTransaction {
                dao.insertMovie(mapp(movieEntityMapper::map))
                saveCacheGenresOfMovie(id, genres)
                keywords?.let {
                    saveCacheKeywordsOfMovie(id, it)
                }
            }
        }
    }

    override suspend fun getMovie(movieId: Int): Movie =
        dao.getMovieById(movieId).mapp(movieEntityOrmMapper::map)

    override suspend fun deleteAllMovies() {
        dao.deleteAll()
    }
}