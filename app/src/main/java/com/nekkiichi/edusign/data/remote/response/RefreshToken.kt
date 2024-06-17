package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse (
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: String?
)
