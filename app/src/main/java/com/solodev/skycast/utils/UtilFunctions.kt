package com.solodev.skycast.utils

fun kelvinToCelsius(kelvin: Double): Double {
    return kelvin - 273.15
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    return email.matches(emailRegex.toRegex())
}