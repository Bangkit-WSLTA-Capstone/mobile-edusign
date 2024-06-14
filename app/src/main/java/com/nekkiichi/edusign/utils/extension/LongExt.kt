package com.nekkiichi.edusign.utils.extension

import java.util.Locale

fun Long.toFormattedTime(): String {
    val sec = ((this / 1000) % 60).toInt()
    val min = ((this / (1000 * 60)) % 60).toInt()
    val h = ((this / (1000* 60 * 60)) % 24).toInt()
    return if(h > 0) {
        String.format(Locale.ENGLISH,"%02d.%02d:%02d", h, min, sec)
    }else {
        String.format(Locale.ENGLISH,"00.%02d:%02d", min,sec)
    }
}