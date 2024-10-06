package com.solodev.skycast.presentation.features.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.solodev.skycast.domain.usecase.WeatherUseCase
import com.solodev.skycast.presentation.features.home.state.ForecastState
import com.solodev.skycast.presentation.features.home.state.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
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

    fun getWeatherData(latitude: Double, longitude: Double) {
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
                            weatherMain = weatherResponse.weather.firstOrNull()?.main,
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

    fun getWeatherForecast(latitude: Double, longitude: Double) {
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