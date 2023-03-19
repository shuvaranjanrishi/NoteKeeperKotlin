package com.therishideveloper.notekeeperkotlin.di

import com.therishideveloper.notekeeperkotlin.api.AuthInterceptor
import com.therishideveloper.notekeeperkotlin.api.NoteApi
import com.therishideveloper.notekeeperkotlin.api.UserApi
import com.therishideveloper.notekeeperkotlin.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Shuva Ranjan Rishi on 16,March,2023
 * BABL, Bangladesh,
 */

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserApi(retrofitBuilder: Retrofit.Builder): UserApi {
        return retrofitBuilder.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providesNoteApi(retrofitBuilder: Retrofit.Builder, okHttpClient: OkHttpClient): NoteApi {
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(NoteApi::class.java)
    }
}