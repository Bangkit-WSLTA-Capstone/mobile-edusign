package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @field:SerializedName("status")
    val status: Boolean?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: DictionaryItem?
)


data class DictionaryItem(
    @field:SerializedName("filelink")
    val imgUrl: String?
)