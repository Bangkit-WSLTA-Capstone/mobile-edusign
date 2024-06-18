package com.nekkiichi.edusign.data.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nekkiichi.edusign.BuildConfig
import com.nekkiichi.edusign.Constant
import com.nekkiichi.edusign.data.local.AuthManager
import com.nekkiichi.edusign.data.remote.response.RefreshTokenResponse
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.net.HttpURLConnection
import java.time.Duration


class AuthInterceptor(private val authManager: AuthManager) : Interceptor {
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            },
        )
    ).build()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (
            originalRequest.url.pathSegments[0] == "login"
            ||
            originalRequest.url.pathSegments[0] == "register"
            ||
            originalRequest.url.pathSegments[0] == "refresh"
        ) {
            return chain.proceed(originalRequest)
        }

        return runBlocking {
            // return response if access token valid
            val response = doRequestWithAccessToken(chain)
            if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED) {
                return@runBlocking response
            }

            //if its unauthorized, get refresh token
            val refreshTokenSucceed = getRefreshToken()

            // if refresh token is also invalid, logout this account
            if (!refreshTokenSucceed) {
                authManager.logout()
                return@runBlocking response
            }
            response.close()

            // retry previous request when new access token is retrieved
            val retryResponse = doRequestWithAccessToken(chain)
            if (retryResponse.code != HttpURLConnection.HTTP_UNAUTHORIZED) {
                return@runBlocking retryResponse
            }
            // if this retryResponse is also unauthorized, just do logout
            authManager.logout()
            return@runBlocking retryResponse
        }
    }

    private suspend fun doRequestWithAccessToken(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = authManager.getToken()
        requestBuilder.addHeader("Authorization", "Bearer $token")
        val response = chain.proceed(requestBuilder.build())
        return response
    }

    private suspend fun getRefreshToken(): Boolean {
        Log.d(TAG, "Get Refresh Token...")
        val refreshToken = authManager.getRefreshToken()
        val refreshRequest = Request.Builder()
            .post("".toRequestBody("application/json".toMediaType()))
            .url("${BuildConfig.API_URL}refresh")
            .addHeader("Authorization", "Bearer $refreshToken")
            .build()
        try {
            val result = okHttpClient.newCall(refreshRequest).execute()
            if (
                result.code == HttpURLConnection.HTTP_OK
            ) {
                Log.d(TAG, "New token retrieved")
                try {
                    val newToken = Gson().fromJson(
                        result.body.toString(),
                        TypeToken.get(RefreshTokenResponse::class.java)
                    )
                    Log.d(TAG, "New token saved")
                    authManager.saveToken(newToken.data ?: "", refreshToken)
                    return true
                } catch (e: Exception) {
                    Log.d(TAG, "parsing failed", e)
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    companion object {
        private const val TAG = "ApiConfig.AuthInterceptor"
    }

}

object ApiConfig {
    fun provideApiService(
        authInterceptor: AuthInterceptor
    ): ApiService {

//        val authInterceptor: Interceptor = Interceptor { chain ->
//
//            val originalRequest = chain.request()
//            if (
//                originalRequest.url.pathSegments[0] == "login"
//                ||
//                originalRequest.url.pathSegments[0] == "register"
//            ) {
//                return@Interceptor chain.proceed(originalRequest)
//            }
//
//            // by default, use
//            if (originalRequest.url.pathSegments[0] == "refresh") {
//                return@Interceptor runBlocking {
//                    val refreshRequestBuilder = chain.request().newBuilder()
//                    val refreshToken = authManager.getRefreshToken()
//                    refreshRequestBuilder.addHeader("Authorization", "Bearer $refreshToken")
//                    val response = chain.proceed(refreshRequestBuilder.build())
//                    if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                        authManager.logout()
//                    }
//                    response
//                }
//            }
//
//            return@Interceptor runBlocking {
//                val requestBuilder = chain.request().newBuilder()
//                val token = authManager.getToken()
//                requestBuilder.addHeader("Authorization", "Bearer $token")
//                val response = chain.proceed(requestBuilder.build())
//                if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                    authManager.logout()
//                }
//                response
//            }
//
//        }

        val baseUrl = Constant.apiUrl
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                },
            )
        val okhttp = OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(15))
            .writeTimeout(Duration.ofSeconds(15))
            .build()
        val retrofit =
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttp)
                .build()

        return retrofit.create(ApiService::class.java)
    }
}