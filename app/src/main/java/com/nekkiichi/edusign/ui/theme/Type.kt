package com.nekkiichi.edusign.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.nekkiichi.edusign.R

// imported fonts
var geistMonoFamily = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.geist_mono_black,FontWeight.Black),
    androidx.compose.ui.text.font.Font(R.font.geist_mono_bold, FontWeight.Bold),
    androidx.compose.ui.text.font.Font(R.font.geist_mono_semibold, FontWeight.SemiBold),
    androidx.compose.ui.text.font.Font(R.font.geist_mono_medium,FontWeight.Medium),
    androidx.compose.ui.text.font.Font(R.font.geist_mono_regular, FontWeight.Normal),
    androidx.compose.ui.text.font.Font(R.font.geist_mono_light, FontWeight.Light),
    androidx.compose.ui.text.font.Font(R.font.geist_mono_thin, FontWeight.Thin)
)

val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

val bodyFontFamily = FontFamily(
    Font(
        googleFont = GoogleFont("Inter"),
        fontProvider = provider,
    )
)

//val displayFontFamily = FontFamily(
//    Font(
//        googleFont = GoogleFont("JetBrains Mono"),
//        fontProvider = provider,
//    )
//)

// Default Material 3 typography values
private val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = geistMonoFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = geistMonoFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = geistMonoFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = geistMonoFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = geistMonoFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = geistMonoFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = geistMonoFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = geistMonoFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = geistMonoFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),

)

