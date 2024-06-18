package com.nekkiichi.edusign.data

import android.util.Log
import com.nekkiichi.edusign.data.entities.TranslateHistory
import com.nekkiichi.edusign.data.local.AuthManager
import com.nekkiichi.edusign.data.remote.ApiService
import com.nekkiichi.edusign.data.remote.request.LoginRequest
import com.nekkiichi.edusign.data.remote.request.RegisterRequest
import com.nekkiichi.edusign.data.remote.response.CourseItem
import com.nekkiichi.edusign.data.remote.response.LoginResponse
import com.nekkiichi.edusign.data.remote.response.RegisterResponse
import com.nekkiichi.edusign.utils.Status
import com.nekkiichi.edusign.utils.extension.parseToMessage
import com.nekkiichi.edusign.utils.extension.toDate
import com.nekkiichi.edusign.utils.formatToLocalDateFromISOString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.Instant
import java.util.Date
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
            authManager.saveToken(temp.data?.accessToken ?: "", temp.data?.refreshToken ?: "")
            Status.Success(temp)
        } catch (e: Exception) {

            Status.Failed(e.parseToMessage())
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
            Status.Failed(e.parseToMessage())
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
            Status.Failed(e.parseToMessage())
        }
        emit(result)
    }

    fun translateVideo(videoFile: File) = flow {
        emit(Status.Loading)
        val result = try {
            val videoRequestFile = videoFile.asRequestBody("video/*".toMediaType())
            val videoPartFile =
                MultipartBody.Part.createFormData("video", videoFile.name, videoRequestFile)
            val result = apiService.uploadVideoTranslate(videoPartFile)
            Status.Success(result)
        } catch (e: Exception) {
            Status.Failed(e.parseToMessage())
        }
        emit(result)
    }

    fun getTranslateHistories(): Flow<Status<List<TranslateHistory>>> = flow {
        emit(Status.Loading)
        val result = try {
            val result = apiService.getHistory()
            val lists = result.data?.map { data ->
                TranslateHistory(
                    fileURl = data.fileLink,
                    result = data.result,
                    dateCreated = data.createdAt.toDate() ?: Date.from(Instant.now())
                )
            } ?: listOf()

            Status.Success(lists)
        } catch (e: Exception) {
            Status.Failed(e.parseToMessage())
        }
        emit(result)
    }

    fun getCourses() = flow {
        emit(Status.Loading)
        val result: Status<List<CourseItem>> = try {
            val result = apiService.getCourses()
            val list = result.data?.map { value ->
                CourseItem(
                    coursename = value.coursename,
                    createdAt = formatToLocalDateFromISOString(value.createdAt)
                )
            } ?: listOf()
            Status.Success(list)
        } catch (e: Exception) {
            Status.Failed(e.parseToMessage())

        }
        emit(result)
    }

    fun getCourse(filename: String) = flow {
        emit(Status.Loading)
        val result = try {
            val markdownString = apiService.getCourseMarkdown(filename)

            Status.Success(markdownString)
        } catch (e: Exception) {
            Log.e(TAG, "getCourse: error", e)
            Status.Failed(e.parseToMessage())
        }
        emit(result)
    }

    fun getDictionary(word: String): Flow<Status<String>> = flow {
        emit(Status.Loading)

        val result = try {
            val response = apiService.getDictionary(word)
            val link = response.data?.imgUrl ?: ""
            Status.Success(link)
        } catch (e: Exception) {
            Status.Failed(e.parseToMessage())
        }

        emit(result)
    }

    companion object {
        private const val TAG = "EdusignRepository"
    }
}