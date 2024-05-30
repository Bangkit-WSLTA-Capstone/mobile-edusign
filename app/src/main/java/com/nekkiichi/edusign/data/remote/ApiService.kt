package com.nekkiichi.edusign.data.remote

import retrofit2.http.GET


interface ApiService     {
    @GET("history")
    suspend fun getHistory(): String
}