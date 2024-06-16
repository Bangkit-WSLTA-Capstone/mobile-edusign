package com.nekkiichi.edusign.data.entities

import java.util.Date

data class TranslateHistory(
    val fileURl: String,
    val result: String,
    val dateCreated: Date
)