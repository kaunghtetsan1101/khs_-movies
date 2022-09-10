package com.khs.movies.appbase.helper

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlin.math.max

fun View.circularRevealedAtCenter(@ColorRes bgColor: Int? = null) {
    val cx = (left + right) / 2
    val cy = (top + bottom) / 2
    val finalRadius = max(width, height)

    if (isAttachedToWindow) {
        val anim = ViewAnimationUtils.createCircularReveal(this, cx, cy, 0f, finalRadius.toFloat())
        isVisible = true
        bgColor?.let {
            setBackgroundColor(ContextCompat.getColor(this.context, it))
        }
        anim.duration = 550
        anim.start()
    }
}

fun View.requestGlideListener(@ColorRes bgColor: Int? = null): RequestListener<Drawable> {
    return object : RequestListener<Drawable> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Drawable>?,
            isFirstResource: Boolean
        ): Boolean = false

        override fun onResourceReady(
            resource: Drawable?,
            model: Any?,
            target: Target<Drawable>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            circularRevealedAtCenter(bgColor)
            return false
        }
    }
}

fun ChipGroup.addChip(
    chipText: String,
    @StyleRes textStyle: Int? = null,
    @ColorRes bgColor: Int? = null
) {
    addView(
        Chip(context).apply {
            text = chipText
            isCheckable = false
            textStyle?.let { setTextAppearanceResource(it) }
            bgColor?.let { setChipBackgroundColorResource(it) }
        })
}

fun ImageView.loadWithCircularRevealed(path: String) {
    Glide.with(context).load(com.khs.movies.network.api.Api.getBackdropPath(path))
        .into(this)
}

fun View.hideSoftKeyboard() {
    post {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
