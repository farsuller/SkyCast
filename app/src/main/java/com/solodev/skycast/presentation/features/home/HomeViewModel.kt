package com.solodev.skycast.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.skycast.domain.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
) : ViewModel() {

    private val _weatherState = MutableStateFlow(WeatherState())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    init {
        getWeatherData("Manila,ph")
    }

    private fun getWeatherData(city: String) {
        viewModelScope.launch {
            weatherUseCase.getWeather(city)
                .onStart {
                    _weatherState.value = WeatherState(isLoading = true)
                }
                .catch { e ->
                    _weatherState.value = WeatherState(errorMessage = e.message)
                }
                .collect { result ->
                    result.body()?.let { weatherResponse ->
                        _weatherState.value = WeatherState(
                            cityName = weatherResponse.name,
                            temperature = weatherResponse.main.temp,
                            feelsLike = weatherResponse.main.feelsLike,
                            tempMin = weatherResponse.main.tempMin,
                            tempMax = weatherResponse.main.tempMax,
                            pressure = weatherResponse.main.pressure,
                            humidity = weatherResponse.main.humidity,
                            windSpeed = weatherResponse.wind.speed,
                            windDeg = weatherResponse.wind.deg,
                            weatherDescription = weatherResponse.weather.firstOrNull()?.description,
                            cloudiness = weatherResponse.clouds.all,
                            sunrise = weatherResponse.sys.sunrise.toLong(),
                            sunset = weatherResponse.sys.sunset.toLong(),
                            country = weatherResponse.sys.country,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

}