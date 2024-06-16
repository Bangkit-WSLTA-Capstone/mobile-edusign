package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @field:SerializedName("status")
    val status: Boolean?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: TranslationBodyResponse?
)

data class TranslationBodyResponse(
    @field:SerializedName("Result")
    val result: String?
)