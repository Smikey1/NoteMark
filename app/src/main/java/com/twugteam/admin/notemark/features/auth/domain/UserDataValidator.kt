package com.twugteam.admin.notemark.features.auth.domain

class UserDataValidator(
    private val patternValidator: PatternValidator
) {
    companion object {
        const val MIN_USERNAME_LENGTH = 3
        const val MIN_PASSWORD_LENGTH = 8
    }

    fun isValidEmail(email: String): Boolean {
        return patternValidator.matches(email.trim())
    }

    fun validatePassword(password: String): PasswordValidationState {
        val hasMinimumLength = password.length >= MIN_PASSWORD_LENGTH
        val hasNumber = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isDigit() && !it.isLetter() }

        return PasswordValidationState(
            hasNumber = hasNumber,
            hasSymbol = hasSymbol,
            hasMinimumLength = hasMinimumLength
        )
    }

    fun isUsernameValid(username: String): Boolean = username.length >= MIN_USERNAME_LENGTH

    fun isValidConfirmPassword(password: String, confirmPassword: String): Boolean {
        val bothPasswordFieldsAreNotEmpty = password.isNotEmpty() && confirmPassword.isNotEmpty()
        return bothPasswordFieldsAreNotEmpty && password == confirmPassword
    }
}