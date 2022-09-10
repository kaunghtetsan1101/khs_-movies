package com.khs.movies.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.khs.movies.domain.model.movie.DiscoverMovie
import com.khs.movies.network.api.Api

@BindingAdapter("poster")
fun bindPoster(view: ImageView, movie: DiscoverMovie?) {
    movie?.posterPath?.let {
        val url = Api.getPosterPath(it)
        Glide.with(view.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}