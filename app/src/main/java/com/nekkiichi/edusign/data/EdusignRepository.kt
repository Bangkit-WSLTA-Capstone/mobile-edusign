package com.nekkiichi.edusign.data

import com.nekkiichi.edusign.data.entities.TranslateHistory
import com.nekkiichi.edusign.data.remote.ApiService
import com.nekkiichi.edusign.utils.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class EdusignRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun login(email: String, password: String) {

    }

    fun register(username: String, email: String, password: String) {

    }

    // remove token from android app
    fun logout() {

    }


    fun translateVideo(videoFile: File) {

    }

    fun getTranslateHistories(): Flow<Status<List<TranslateHistory>>> = flow {
        emit(Status.Loading)
        try {
            val result = apiService.getHistory()
            emit(Status.Success(listOf()))
        } catch (e: Exception) {
            emit(Status.Failed("Error message"))
        }
    }
}