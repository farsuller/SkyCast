package com.solodev.skycast.presentation.features.home

data class WeatherState(
    val cityName: String = "",
    val temperature: Double = 0.0,
    val feelsLike: Double = 0.0,
    val tempMin: Double = 0.0,
    val tempMax: Double = 0.0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Double = 0.0,
    val windDeg: Int = 0,
    val weatherDescription: String? = null,
    val cloudiness: Int = 0,
    val sunrise: Long = 0,
    val sunset: Long = 0,
    val country: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
