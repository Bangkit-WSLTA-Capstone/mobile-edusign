package com.nekkiichi.edusign.utils

import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timestamp: String =
    SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis())


object FileExt {
    /**
     * Return temp empty video file at `data/data/{packageId}/cache/video_timestamp.mp4`
     *
     * use current timestamp by default, with format "`yyyyMMdd_HHmmss`"
     */
    fun createTempVideoFile(context: Context): File {
        val videoFile = File.createTempFile("video_${timestamp}", ".mp4", context.cacheDir)
        return videoFile
    }

}
