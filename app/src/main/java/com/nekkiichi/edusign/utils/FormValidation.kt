package com.nekkiichi.edusign.utils


/**
 * Check if string is valid email address
 * Return null if valid, otherwise return reason string if not valid.
 */
fun String.isValidEmail(): String? {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
    val result = this.matches(emailRegex.toRegex())
    if(result) {
        return null
    }
    return "This is not valid email"
}

/**
 * Check if password is meet this criteria below:
 * - have at least 8 characters
 *
 * return null if valid, return string if not valid
 */
fun String.isValidPassword(): String? {
    if(this.length < 8) {
        return "Password must contain at least 8 character"
    }
    return null
}