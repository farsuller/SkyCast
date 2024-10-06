package com.solodev.skycast.domain.model

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

val cities = listOf(
    City("Makati City", 14.5547, 121.0244),
    City("Cebu City", 10.3157, 123.8854),
    City("Manila", 14.5995, 120.9842),
    City("Pasig City", 14.5733, 121.0583),
)

