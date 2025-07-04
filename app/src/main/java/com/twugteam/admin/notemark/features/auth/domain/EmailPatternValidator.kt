package com.twugteam.admin.notemark.features.auth.domain

object EmailPatternValidator : PatternValidator {
    private val STRICT_EMAIL_REGEX =
        Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    override fun matches(values: String): Boolean {
        return STRICT_EMAIL_REGEX.matches(values.trim())
    }
}