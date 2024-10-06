package com.solodev.skycast.domain.model.forecast

import com.google.gson.annotations.SerializedName
import com.solodev.skycast.domain.model.Clouds
import com.solodev.skycast.domain.model.Main
import com.solodev.skycast.domain.model.Weather
import com.solodev.skycast.domain.model.Wind

data class Forecast(
    val clouds: Clouds,
    val dt: Int,

    @SerializedName("dt_txt")
    val dateTxt: String,

    val main: Main,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)