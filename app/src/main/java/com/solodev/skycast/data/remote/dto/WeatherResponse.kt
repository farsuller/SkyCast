package com.solodev.skycast.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.solodev.skycast.domain.model.Clouds
import com.solodev.skycast.domain.model.Coordinates
import com.solodev.skycast.domain.model.Main
import com.solodev.skycast.domain.model.Sys
import com.solodev.skycast.domain.model.Weather
import com.solodev.skycast.domain.model.Wind

data class WeatherResponse(
    val base: String,

    val clouds: Clouds,

    val cod: Int,

    @SerializedName("coord")
    val coord: Coordinates,

    val dt: Int,

    val id: Int,

    val main: Main,

    val name: String,

    val sys: Sys,

    val timezone: Int,

    val visibility: Int,

    @SerializedName("weather")
    val weather: List<Weather>,

    val wind: Wind
)










