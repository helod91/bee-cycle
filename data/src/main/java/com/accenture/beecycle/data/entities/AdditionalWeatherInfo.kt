package com.accenture.beecycle.data.entities

import com.google.gson.annotations.SerializedName

data class AdditionalWeatherInfo(
    @SerializedName("temp") val temperature: Double? = null,
    @SerializedName("feels_like") val feelsLike: Double? = null,
    @SerializedName("temp_min") val tempMin: Double? = null,
    @SerializedName("temp_max") val tempMax: Double? = null,
    @SerializedName("pressure") val pressure: Int? = null,
    @SerializedName("humidity") val humidity: Int? = null,
    @SerializedName("sea_level") val seaLevel: Int? = null,
    @SerializedName("grnd_level") val groundLevel: Int? = null
)