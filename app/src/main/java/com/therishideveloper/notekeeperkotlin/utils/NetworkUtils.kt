package com.therishideveloper.notekeeperkotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

class NetworkUtils {

    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                return this.getNetworkCapabilities(this.activeNetwork)?.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET
                ) ?: false
            }
        }
    }
}