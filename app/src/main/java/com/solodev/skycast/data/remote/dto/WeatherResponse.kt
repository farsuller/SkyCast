package com.solodev.skycast.data.remote.dto

import com.solodev.skycast.domain.model.Clouds
import com.solodev.skycast.domain.model.Coordinates
import com.solodev.skycast.domain.model.Main
import com.solodev.skycast.domain.model.today.Sys
import com.solodev.skycast.domain.model.Weather
import com.solodev.skycast.domain.model.Wind

data class WeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coordinates,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)








