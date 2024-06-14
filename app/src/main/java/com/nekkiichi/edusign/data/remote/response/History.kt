package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class History(
    @field:SerializedName("created_at")
    val createAt: String,
    @field:SerializedName("fileLink")
    val fileLink: String,
    @field:SerializedName("result")
    val result: String
)

data class HistoryResponse(
    @field:SerializedName("status")
    val status: String?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: HistoryBodyResponse?
)

data class HistoryBodyResponse(
    @field:SerializedName("histories")
    val histories: List<History>?
)