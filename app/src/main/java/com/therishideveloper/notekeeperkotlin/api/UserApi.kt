package com.therishideveloper.notekeeperkotlin.api

import com.therishideveloper.notekeeperkotlin.model.UserRequest
import com.therishideveloper.notekeeperkotlin.model.UserResponse
import com.therishideveloper.notekeeperkotlin.utils.Constants.POST_URL
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Shuva Ranjan Rishi on 16,March,2023
 * BABL, Bangladesh,
 */

interface UserApi {

    @POST(POST_URL)
    suspend fun singUp(@Body userRequest: UserRequest) : Response<UserResponse>

    @POST("")
    suspend fun singIn(@Body userRequest: UserRequest) : Response<UserResponse>
}