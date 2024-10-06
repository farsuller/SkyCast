package com.solodev.skycast.domain.usecase

import com.solodev.skycast.data.remote.dto.WeatherResponse
import com.solodev.skycast.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetWeather(
    private val repository: WeatherRepository
) {
    operator fun invoke(latitude : Double, longitude : Double, ) : Flow<Response<WeatherResponse>> {
        return repository.getWeather(latitude = latitude, longitude = longitude)
    }
}