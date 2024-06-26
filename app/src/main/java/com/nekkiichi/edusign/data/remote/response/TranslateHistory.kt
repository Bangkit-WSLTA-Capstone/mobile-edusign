package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class TranslateHistory(
    @field:SerializedName("created_at")
    val createdAt: String,
    @field:SerializedName("filelink")
    val fileLink: String,
    @field:SerializedName("result")
    val result: String
)

data class HistoryResponse(
    @field:SerializedName("status")
    val status: Boolean?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: List<TranslateHistory>?
)