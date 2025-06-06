package com.twugteam.admin.notemark.features.auth.data

import android.util.Patterns
import com.twugteam.admin.notemark.features.auth.domain.PatternValidator

object EmailPatternValidator : PatternValidator {
    override fun matches(values: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(values).matches()
    }
}