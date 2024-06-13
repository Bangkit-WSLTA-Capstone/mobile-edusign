package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName

data class Account(
    @field:SerializedName("username")
    val username: String,
    @field:SerializedName("email")
    val email: String
)
