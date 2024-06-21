package com.nekkiichi.edusign

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object Constant {
    val apiUrl = BuildConfig.API_URL
    val defaultNum = 15
    val defaultGap = (defaultNum).dp
    val defaultShape = RoundedCornerShape(defaultGap)
    val defaultShapeByPercent = RoundedCornerShape(percent = defaultNum)
}