package com.khs.movies.domain.usecase.genre

import com.khs.movies.domain.model.Genre
import com.khs.movies.domain.repository.GenreRepository
import javax.inject.Inject

class SaveCacheGenresOfMovie @Inject constructor(
    private val genreRepository: GenreRepository
) {
    suspend operator fun invoke(movieId: Int, genres: List<Genre>) =
        genreRepository.saveCacheGenresOfMovie(movieId, genres)
}