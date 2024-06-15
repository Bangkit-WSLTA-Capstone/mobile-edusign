package com.nekkiichi.edusign.utils

//sealed class for wrapping error and result in one place
sealed class Status<out R> private constructor() {
    data class Success<out T>(val value: T) : Status<T>()
    data class Failed(val errorMessage: String) : Status<Nothing>()
    data object Loading : Status<Nothing>()
}