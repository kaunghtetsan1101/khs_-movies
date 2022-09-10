package com.khs.movies.network.mapper

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.Video
import com.khs.movies.network.response.video.VideoJson
import javax.inject.Inject

class VideoJsonMapper @Inject constructor() : UniMapper<VideoJson, Video> {

    override fun map(item: VideoJson): Video = with(item) {
        Video(
            id = id,
            name = name,
            site = site,
            key = key,
            size = size,
            type = type
        )
    }
}