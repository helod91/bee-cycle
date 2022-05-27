package com.accenture.beecycle.domain.models

data class Weather(
    val imageId: Int? = null,
    val title: String? = null,
    val shortDescription: String? = null,
    val temperature: Double? = null,
    val feelsLike: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val humidity: Int? = null,
    val windSpeed: Double? = null
)