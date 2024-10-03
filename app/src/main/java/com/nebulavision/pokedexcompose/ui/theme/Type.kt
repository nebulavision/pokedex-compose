package com.example.pokedex.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.nebulavision.pokedexcompose.R

// Carga de la fuente Nunito
val nunitoFontFamily = FontFamily(
    Font(R.font.nunito_regular),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_light, FontWeight.Light)
)

// Definición de tipografías
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 57.sp,
        fontWeight = FontWeight.Light,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 45.sp,
        fontWeight = FontWeight.Light,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 36.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.25.sp
    ),
    bodySmall = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.4.sp
    ),
    labelLarge = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    ),
    labelMedium = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = nunitoFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp
    )
)
