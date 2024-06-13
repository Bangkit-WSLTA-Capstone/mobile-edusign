package com.nekkiichi.edusign.data.remote

import com.nekkiichi.edusign.BuildConfig
import com.nekkiichi.edusign.data.local.AuthManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

object ApiConfig {
    fun provideApiService(authManager: AuthManager): ApiService {

        val authInterceptor: Interceptor = Interceptor { chain ->

            val originalRequest = chain.request()
            if (
                originalRequest.url.pathSegments[0] == "login"
                ||
                originalRequest.url.pathSegments[0] == "register"
            ) {
                return@Interceptor chain.proceed(originalRequest)
            }

            return@Interceptor runBlocking {
                val requestBuilder = chain.request().newBuilder()
                val token = authManager.getToken()
                requestBuilder.addHeader("Authorization", "Bearer $token")

                val response = chain.proceed(requestBuilder.build())
                if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    authManager.saveToken("")
                    authManager.onLogout()
                }
                response
            }

        }

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