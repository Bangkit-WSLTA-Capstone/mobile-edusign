package com.nekkiichi.edusign.utils

import android.content.Context

fun Context.getAppName(): String = applicationInfo.loadLabel(packageManager).toString()