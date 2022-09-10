package com.khs.movies.appbase.helper

import android.content.Context
import android.content.res.Configuration


/************* Context *******************/
val Context.isDarkMode: Boolean
    get() = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }