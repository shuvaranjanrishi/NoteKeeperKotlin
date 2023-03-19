package com.therishideveloper.notekeeperkotlin.api

import com.therishideveloper.notekeeperkotlin.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * Created by Shuva Ranjan Rishi on 17,March,2023
 * BABL, Bangladesh,
 */

interface NoteApi {

    @GET("posts")
    suspend fun getNotes() : Response<List<NoteResponse>>

    @POST("posts")
    suspend fun createNote(@Body noteResponse: NoteResponse) : Response<NoteResponse>

    @PUT("posts/{id}")
    suspend fun updateNote(@Path("id") id: String,@Body noteResponse: NoteResponse) : Response<NoteResponse>

    @DELETE("posts")
    suspend fun deleteNote(@Path("id") id:String) : Response<NoteResponse>
}