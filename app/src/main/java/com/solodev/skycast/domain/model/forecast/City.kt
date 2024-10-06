package com.solodev.skycast.domain.model.forecast

import com.solodev.skycast.domain.model.Coordinates

data class City(
    val coord: Coordinates,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)