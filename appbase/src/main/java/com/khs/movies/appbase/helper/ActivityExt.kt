package com.khs.movies.appbase.helper

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

fun Activity.displayToast(
    formatArgs: Array<Any>? = null,
    @StringRes message: Int? = null,
    messageStr: String? = null,
    length: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(
        this,
        message?.let { msg ->
            formatArgs?.let {
                getString(msg, *it)
            } ?: getString(msg)
        } ?: messageStr!!,
        length
    ).show()
}

fun Activity.displaySnackBar(
    formatArgs: Array<Any>? = null,
    @StringRes message: Int? = null,
    messageStr: String? = null,
    @StringRes actionText: Int? = null,
    onClickAction: (View.() -> Unit)? = null,
    length: Int = Snackbar.LENGTH_SHORT
): Snackbar =
    Snackbar.make(
        findViewById(android.R.id.content),
        message?.let { msg ->
            formatArgs?.let {
                resources.getString(msg, *it)
            } ?: resources.getString(msg)
        } ?: messageStr!!,
        length
    ).apply {
        if (actionText != null && onClickAction != null) {
            setAction(
                actionText,
                onClickAction
            )
        }
        show()
    }