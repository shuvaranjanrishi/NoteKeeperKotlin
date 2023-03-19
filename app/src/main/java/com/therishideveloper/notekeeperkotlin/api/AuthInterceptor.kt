package com.therishideveloper.notekeeperkotlin.api

import com.therishideveloper.notekeeperkotlin.session.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

class AuthInterceptor @Inject constructor() : Interceptor {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer " + tokenManager.getToken())
        return chain.proceed(request.build())
    }

}