package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @field:SerializedName("status")
    val status: Boolean?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: LoginDataResponse?
)


data class LoginDataResponse(
    @field:SerializedName("access")
    val accessToken: String?,
    @field:SerializedName("refresh")
    val refreshToken: String?
)