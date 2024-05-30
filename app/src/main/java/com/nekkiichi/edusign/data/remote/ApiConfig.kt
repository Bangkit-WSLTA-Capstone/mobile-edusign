package com.nekkiichi.edusign.data.remote

import com.nekkiichi.edusign.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun provideApiService(): ApiService {
        val baseUrl = ""
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                },
            )
        val okhttp = OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()
        val retrofit =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()

        return retrofit.create(ApiService::class.java)
    }
}