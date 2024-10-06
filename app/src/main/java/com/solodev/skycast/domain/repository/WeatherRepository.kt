package com.solodev.skycast.domain.repository

import com.solodev.skycast.data.remote.dto.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface WeatherRepository {
    fun getWeather(city: String): Flow<Response<WeatherResponse>>
}