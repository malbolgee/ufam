package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val motoFontFamily = FontFamily(Font(familyName = DeviceFontFamilyName("rookery")))
private val rockeryNew = FontFamily(Font(familyName = DeviceFontFamilyName("Rookery-new")))

object FontStyles {

    val generalDescription: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 16.sp,
        fontFamily = rockeryNew,
        fontWeight = FontWeight(400),
    )
}

val ufamTypography = Typography(
    headlineLarge = TextStyle(
        fontSize = 40.sp,
        lineHeight = 46.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(700),
    ),
    headlineMedium = TextStyle(
        fontSize = 32.sp,
        lineHeight = 40.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(700),
    ),
    headlineSmall = TextStyle(
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(700),
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(500),
    ),
    titleMedium = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(500),
        letterSpacing = 0.1.sp,
    ),
    titleSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(700),
        letterSpacing = 0.5.sp,
    ),
    bodyLarge = TextStyle(),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(400),
        letterSpacing = 0.5.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(400),
        letterSpacing = 0.25.sp,
    ),
    labelLarge = TextStyle(
        fontSize = 20.sp,
        lineHeight = 26.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(500),
    ),
    labelSmall = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = motoFontFamily,
        fontWeight = FontWeight(400),
    ),
)