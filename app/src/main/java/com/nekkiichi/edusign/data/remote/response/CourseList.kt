package com.nekkiichi.edusign.data.remote.response

import com.google.gson.annotations.SerializedName


data class CourseListResponse(
    @field:SerializedName("status")
    val status: Boolean?,
    @field:SerializedName("message")
    val message: String?,
    @field:SerializedName("data")
    val data: List<CourseItem>?
)

data class CourseItem(
    @field:SerializedName("coursename")
    val coursename: String,
    @field:SerializedName("created_at")
    val createdAt: String
)