package com.solodev.skycast.domain.usecase

import com.solodev.skycast.domain.usecase.weather.GetWeather

data class WeatherUseCase(
    val getWeather: GetWeather
)
