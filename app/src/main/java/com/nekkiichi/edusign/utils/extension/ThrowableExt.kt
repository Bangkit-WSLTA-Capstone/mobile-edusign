package com.nekkiichi.edusign.utils.extension

import android.util.Log
import com.google.gson.JsonParser
import retrofit2.HttpException
import java.io.IOException

fun Throwable.parseToMessage(): String {
    return when(this) {
        is IOException -> {
            "Network error"
        }
        is HttpException -> {
            val body = this.response()?.errorBody()?.string().toString()
            JsonParser.parseString(body).asJsonObject["message"].asString
        }
        else -> {
            Log.e("Throwable.parseToMessage()", "unknown error", this)
            "unknown error"
        }
    }
}