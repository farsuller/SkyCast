package com.solodev.skycast.presentation.features.home.state

import com.solodev.skycast.domain.model.forecast.City
import com.solodev.skycast.domain.model.forecast.Forecast

data class ForecastState(
    val isLoading: Boolean = false,
    val forecast: List<Forecast>? = null,
    val city : City? = null,
    val error: String? = null
)