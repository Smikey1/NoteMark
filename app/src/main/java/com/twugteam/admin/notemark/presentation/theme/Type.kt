package com.twugteam.admin.notemark.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R

private val bold = Font(resId = R.font.space_grotesk_bold, weight = FontWeight.Bold)
private val light = Font(resId = R.font.space_grotesk_light, weight = FontWeight.Light)
private val medium = Font(resId = R.font.space_grotesk_medium, weight = FontWeight.Medium)
private val normal = Font(resId = R.font.space_grotesk_regular, weight = FontWeight.Normal)
private val semiBold = Font(resId = R.font.space_grotesk_semibold, weight = FontWeight.SemiBold)

private val noteMarkFontFamily = FontFamily(fonts = listOf(bold,light, normal, medium, semiBold))

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = noteMarkFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        color = Color.Black
    ),
    titleMedium = TextStyle(
        fontFamily = noteMarkFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        color = Color.Black
    ),
    titleSmall = TextStyle(
        fontFamily = noteMarkFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        color = Color.Black
    ),
    bodyLarge = TextStyle(
        fontFamily = noteMarkFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        color = Color.Black
    ),
    bodyMedium = TextStyle(
        fontFamily = noteMarkFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = Color.Black
    ),
    bodySmall = TextStyle(
        fontFamily = noteMarkFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = Color.Black
    ),
)