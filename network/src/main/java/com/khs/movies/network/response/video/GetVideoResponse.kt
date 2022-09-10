package com.khs.movies.network.response.video

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetVideoResponse(
    val id: Int,
    val results: List<VideoJson>
)