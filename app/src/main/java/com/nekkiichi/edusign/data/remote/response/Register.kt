package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("message")
    val message: String?
)
