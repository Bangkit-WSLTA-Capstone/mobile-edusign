package com.nekkiichi.edusign.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter = DateTimeFormatter.ISO_DATE_TIME

fun formatToLocalDateFromISOString(dateString: String): String {
    try {
        val formattedDate = LocalDateTime.parse(dateString, formatter)
        return DateTimeFormatter.ofPattern("dd MMMM  yyyy | hh:mma").format(formattedDate)
    } catch (e: Exception) {
        return dateString
    }
}