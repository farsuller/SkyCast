package com.solodev.skycast.domain.usecase

data class WeatherUseCase(
    val getWeather: GetWeather,
    val getWeatherForecast: GetWeatherForecast
)
