package com.nekkiichi.edusign.utils.extension

import android.content.Context

fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()