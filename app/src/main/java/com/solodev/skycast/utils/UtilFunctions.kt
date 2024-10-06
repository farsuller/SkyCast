package com.solodev.skycast.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun kelvinToCelsius(kelvin: Double): Double {
    return kelvin - 273.15
}

fun convertUnixToReadableTime(unixTimestamp: Int, timezoneOffset: Int): String {
    val instant = Instant.ofEpochSecond(unixTimestamp.toLong())
    val zoneId = ZoneId.ofOffset("UTC", java.time.ZoneOffset.ofTotalSeconds(timezoneOffset))
    val formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(zoneId)
    return formatter.format(instant)
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    return email.matches(emailRegex.toRegex())
}