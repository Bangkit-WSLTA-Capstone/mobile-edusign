package com.nekkiichi.edusign.utils.extension

import java.time.Instant
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

fun String.toDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME): Date? {
    try {
        val date = OffsetDateTime.parse(this, formatter)
        return Date.from(Instant.from(date))
    } catch (e: Throwable) {
        return null
    }
}