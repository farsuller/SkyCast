package com.solodev.skycast.data.repository

import com.solodev.skycast.BuildConfig
import com.solodev.skycast.data.remote.WeatherApi
import com.solodev.skycast.data.remote.dto.WeatherResponse
import com.solodev.skycast.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
   private val weatherApi: WeatherApi
) : WeatherRepository {
    override fun getWeather(city: String): Flow<Response<WeatherResponse>> = flow {
        try {
            val response = weatherApi.getWeather(city, BuildConfig.API_KEY)
            emit(response)
        }
        catch (e : Exception){
            val errorResponseBody = (e.message ?: "Unknown error").toResponseBody(null)
            emit(Response.error(500, errorResponseBody))
        }
    }.flowOn(Dispatchers.IO)
}