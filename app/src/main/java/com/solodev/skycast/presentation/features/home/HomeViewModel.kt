package com.solodev.skycast.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.skycast.domain.usecase.WeatherUseCase
import com.solodev.skycast.presentation.features.home.state.ForecastState
import com.solodev.skycast.presentation.features.home.state.WeatherState
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

    private val _forecastState = MutableStateFlow(ForecastState())
    val forecastState: StateFlow<ForecastState> = _forecastState.asStateFlow()

    init {
        getWeatherData(latitude = 14.5964947, longitude = 120.9383599)
        getWeatherForecast(latitude = 14.5964947, longitude = 120.9383599)
    }

    private fun getWeatherData(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherUseCase.getWeather(latitude = latitude, longitude = longitude)
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
                            sunrise = weatherResponse.sys.sunrise,
                            sunset = weatherResponse.sys.sunset,
                            timezone = weatherResponse.timezone,
                            country = weatherResponse.sys.country,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    private fun getWeatherForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            weatherUseCase.getWeatherForecast(latitude, longitude)
                .onStart {
                    _forecastState.value = ForecastState(isLoading = true)
                }
                .catch { e ->
                    _forecastState.value = ForecastState(error = e.message)
                }
                .collect{
                        response ->
                    if (response.isSuccessful) {
                        response.body()?.let { forecastResponse ->
                            _forecastState.value = ForecastState(
                                forecast = forecastResponse.list,
                                city = forecastResponse.city)
                        } ?: run {
                            _forecastState.value = ForecastState(error = "No data available")
                        }
                    } else {
                        _forecastState.value = ForecastState(error = "Error: ${response.code()}")
                    }
                }
        }
    }

}