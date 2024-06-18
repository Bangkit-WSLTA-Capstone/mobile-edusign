package com.nekkiichi.edusign.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import com.nekkiichi.edusign.R

// imported fonts
var plusJakartaSansFamily = FontFamily(
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_extrabold, FontWeight.ExtraBold),
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_medium,FontWeight.Medium),
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_light, FontWeight.Light),
    androidx.compose.ui.text.font.Font(R.font.plus_jakarta_sans_extralight, FontWeight.ExtraLight)
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
    displayLarge = baseline.displayLarge.copy(fontFamily = plusJakartaSansFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = plusJakartaSansFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = plusJakartaSansFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = plusJakartaSansFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = plusJakartaSansFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = plusJakartaSansFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = plusJakartaSansFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = plusJakartaSansFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = plusJakartaSansFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = plusJakartaSansFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = plusJakartaSansFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = plusJakartaSansFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = plusJakartaSansFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = plusJakartaSansFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = plusJakartaSansFamily),

)

