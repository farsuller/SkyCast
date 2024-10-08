package com.solodev.skycast.presentation.features.home.state

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
    val weatherMain : String? = null,
    val weatherDescription: String? = null,
    val cloudiness: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val timezone: Int = 0,
    val country: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
