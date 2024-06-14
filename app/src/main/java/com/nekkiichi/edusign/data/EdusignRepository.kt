package com.nekkiichi.edusign.data

import com.nekkiichi.edusign.data.entities.TranslateHistory
import com.nekkiichi.edusign.data.local.AuthManager
import com.nekkiichi.edusign.data.remote.ApiService
import com.nekkiichi.edusign.data.remote.request.LoginRequest
import com.nekkiichi.edusign.data.remote.request.RegisterRequest
import com.nekkiichi.edusign.data.remote.response.LoginResponse
import com.nekkiichi.edusign.data.remote.response.RegisterResponse
import com.nekkiichi.edusign.utils.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class EdusignRepository @Inject constructor(
    private val apiService: ApiService,
    private val authManager: AuthManager
) {

    fun login(email: String, password: String): Flow<Status<LoginResponse>> = flow {
        emit(Status.Loading)
        val result = try {
            val body = LoginRequest(email, password)
            val temp = apiService.login(body)
            Status.Success(temp)
        } catch (e: Exception) {
            Status.Failed(e.message.toString())
        }
        emit(result)
    }

    fun register(
        username: String,
        email: String,
        password: String
    ): Flow<Status<RegisterResponse>> = flow {
        emit(Status.Loading)
        val result = try {
            val body = RegisterRequest(username, email, password)
            val temp = apiService.register(body)
            Status.Success(temp)
        } catch (e: Exception) {
            Status.Failed("Failed to fetch error message")
        }
        emit(result)
    }

    // remove token from android app
    fun logout() = flow {
        emit(Status.Loading)
        val result = try {
            authManager.logout()
            Status.Success("Logout complete")
        } catch (e: Exception) {
            Status.Failed(e.message.toString())
        }
        emit(result)
    }


    fun translateVideo(videoFile: File) = flow {
        emit(Status.Loading)
        val result = try {
            val videoRequestFile = videoFile.asRequestBody("video/mp4".toMediaType())
            val videoPartFile = MultipartBody.Part.create(videoRequestFile)
            val result = apiService.uploadVideoTranslate(videoPartFile)
            Status.Success(result)
        }catch (e: Exception) {
            Status.Failed(e.message.toString())
        }
        emit(result)
    }

    fun getTranslateHistories(): Flow<Status<List<TranslateHistory>>> = flow {
        emit(Status.Loading)
        val result = try {
            val result = apiService.getHistory()
            val lists = result.data?.histories?.map { data ->
                TranslateHistory(
                    fileURl = data.fileLink,
                    result = data.result
                )
            } ?: listOf()

            Status.Success(lists)
        } catch (e: Exception) {
            Status.Failed("Error message")
        }
        emit(result)
    }
}