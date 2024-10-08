package com.solodev.skycast.domain.repository

import com.solodev.skycast.data.remote.dto.ForecastResponse
import com.solodev.skycast.data.remote.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface WeatherRepository {
    fun getWeather(latitude : Double, longitude : Double): Flow<Response<WeatherResponse>>
    fun getWeatherForecast(latitude : Double, longitude : Double) : Flow <Response<ForecastResponse>>
}