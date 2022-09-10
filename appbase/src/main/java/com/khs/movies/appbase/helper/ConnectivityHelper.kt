package com.khs.movies.appbase.helper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.InetAddress

object ConnectivityHelper {

    fun isOnline(): Boolean = runBlocking {
        return@runBlocking withContext(Dispatchers.IO) {
            try {
                val ipAddr: InetAddress = InetAddress.getByName("google.com")
                !ipAddr.equals("")
            } catch (e: Exception) {
                false
            }
        }
    }
}