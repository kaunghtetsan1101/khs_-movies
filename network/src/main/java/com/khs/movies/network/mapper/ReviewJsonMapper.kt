package com.khs.movies.network.mapper

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.Review
import com.khs.movies.network.response.review.ReviewJson
import javax.inject.Inject

class ReviewJsonMapper @Inject constructor() : UniMapper<ReviewJson, Review> {

    override fun map(item: ReviewJson): Review = with(item) {
        Review(
            id = id,
            author = author,
            content = content,
            url = url
        )
    }
}