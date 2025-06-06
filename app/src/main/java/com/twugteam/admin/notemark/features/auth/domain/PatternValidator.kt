package com.twugteam.admin.notemark.features.auth.domain

interface PatternValidator {
    fun matches(values: String): Boolean
}