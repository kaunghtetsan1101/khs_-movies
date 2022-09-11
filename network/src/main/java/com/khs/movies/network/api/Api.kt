package com.khs.movies.network.api

object Api {
    private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
    private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"
    private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
    private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"

    const val NETWORK_TIMEOUT = 10000L
    const val BASE_API_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "9f04c49e56282d595c3ac1fa31ea742d"

    fun getPosterPath(posterPath: String): String {
        return BASE_POSTER_PATH + posterPath
    }

    fun getBackdropPath(backdropPath: String): String {
        return BASE_BACKDROP_PATH + backdropPath
    }

    fun getYoutubeVideoPath(videoPath: String): String {
        return YOUTUBE_VIDEO_URL + videoPath
    }

    fun getYoutubeThumbnailPath(thumbnailPath: String): String {
        return "$YOUTUBE_THUMBNAIL_URL$thumbnailPath/default.jpg"
    }
}
