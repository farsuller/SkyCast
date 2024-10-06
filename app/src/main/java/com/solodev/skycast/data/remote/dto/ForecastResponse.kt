package com.solodev.skycast.data.remote.dto

import com.solodev.skycast.domain.model.forecast.City
import com.solodev.skycast.domain.model.forecast.Forecast
data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Int
)