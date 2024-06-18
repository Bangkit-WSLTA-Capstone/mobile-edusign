package com.nekkiichi.edusign.data.remote

import com.nekkiichi.edusign.data.remote.request.LoginRequest
import com.nekkiichi.edusign.data.remote.request.RegisterRequest
import com.nekkiichi.edusign.data.remote.response.CourseListResponse
import com.nekkiichi.edusign.data.remote.response.HistoryResponse
import com.nekkiichi.edusign.data.remote.response.LoginResponse
import com.nekkiichi.edusign.data.remote.response.RefreshTokenResponse
import com.nekkiichi.edusign.data.remote.response.RegisterResponse
import com.nekkiichi.edusign.data.remote.response.TranslationResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface ApiService{

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse

    @POST("refresh")
    suspend fun refreshToken(): RefreshTokenResponse

    @Multipart
    @POST("translate")
    suspend fun uploadVideoTranslate(
        @Part video: MultipartBody.Part
    ): TranslationResponse

    @GET("translate")
    suspend fun getHistory(): HistoryResponse

    @GET("course")
    suspend fun getCourses(): CourseListResponse

    @GET("course/{filename}")
    suspend fun getCourseMarkdown(@Path("filename") filename: String): String
}