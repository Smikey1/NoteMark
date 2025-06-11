package com.twugteam.admin.notemark.core.presentation.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R

private val spaceGroteskBold = Font(resId = R.font.space_grotesk_bold, weight = FontWeight.Bold)
private val spaceGroteskLight = Font(resId = R.font.space_grotesk_light, weight = FontWeight.Light)
private val spaceGroteskMedium =
    Font(resId = R.font.space_grotesk_medium, weight = FontWeight.Medium)
private val spaceGroteskNormal =
    Font(resId = R.font.space_grotesk_regular, weight = FontWeight.Normal)
private val spaceGroteskSemiBold =
    Font(resId = R.font.space_grotesk_semibold, weight = FontWeight.SemiBold)

private val spaceGroteskFontFamily =
    FontFamily(
        fonts = listOf(
            spaceGroteskBold,
            spaceGroteskLight,
            spaceGroteskNormal,
            spaceGroteskMedium,
            spaceGroteskSemiBold
        )
    )

private val interBold = Font(resId = R.font.inter_bold, weight = FontWeight.Bold)
private val interLight = Font(resId = R.font.inter_light, weight = FontWeight.Light)
private val interMedium =
    Font(resId = R.font.inter_medium, weight = FontWeight.Medium)
private val interNormal =
    Font(resId = R.font.inter_regular, weight = FontWeight.Normal)
private val interSemiBold =
    Font(resId = R.font.inter_semibold, weight = FontWeight.SemiBold)

private val interFontFamily =
    FontFamily(fonts = listOf(interBold, interLight, interNormal, interMedium, interSemiBold))


val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = spaceGroteskFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 40.sp,
        color = OnSurface
    ),
    titleMedium = TextStyle(
        fontFamily = spaceGroteskFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 36.sp,
        color = OnSurface
    ),
    titleSmall = TextStyle(
        fontFamily = spaceGroteskFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        color = OnSurface
    ),
    bodyLarge = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        color = OnSurface
    ),
    bodyMedium = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = OnSurface
    ),
    bodySmall = TextStyle(
        fontFamily = interFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
        color = OnSurface
    ),
)