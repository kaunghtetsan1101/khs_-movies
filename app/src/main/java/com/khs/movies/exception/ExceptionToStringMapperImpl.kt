package com.khs.movies.exception

import android.content.Context
import com.khs.movies.network.R

import com.khs.movies.domain.exception.ExceptionToStringMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ExceptionToStringMapperImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ExceptionToStringMapper {

    override fun map(item: Throwable): String {
        return when (item) {
            is UnknownHostException -> context.getString(R.string.error_no_internet)
            is SocketTimeoutException -> context.getString(R.string.error_network_timeout)
            is ConnectException -> context.getString(R.string.error_no_internet)
            is HttpException -> parseHttpError(item)
            else -> context.getString(R.string.error_unknown)
        }
    }

    private fun parseHttpError(exception: HttpException): String {
        return when (exception.code()) {
            500 -> context.getString(R.string.error_server_500)
            else -> exception.response()?.errorBody()
                ?.let { parseMessageFromErrorBody(it.string()) }
                ?: context.getString(
                    R.string.error_unknown
                )
        }
    }

    private fun parseMessageFromErrorBody(errorBody: String?): String {
        return errorBody?.let {
            try {
                JSONObject(errorBody).getString("status_message")
            } catch (exception: Exception) {
                Timber.e(exception)
                context.getString(R.string.error_unknown)
            }
        } ?: context.getString(R.string.error_unknown)
    }

}