package com.solodev.skycast.data.remote

import com.solodev.skycast.data.remote.dto.ForecastResponse
import com.solodev.skycast.data.remote.dto.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("APPID") apiKey: String
    ): Response<WeatherResponse>

    @GET("data/2.5/forecast")
    suspend fun getWeatherForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String
    ): Response<ForecastResponse>
}