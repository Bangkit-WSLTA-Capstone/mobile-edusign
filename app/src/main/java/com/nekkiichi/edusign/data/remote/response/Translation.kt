package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class TranslationResponse(
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: TranslationBodyResponse?
)

data class TranslationBodyResponse(
    @field:SerializedName("history")
    val history: History
)