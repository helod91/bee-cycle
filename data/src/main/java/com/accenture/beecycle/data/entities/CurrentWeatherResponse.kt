package com.accenture.beecycle.data.entities

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("weather") val basicWeatherInfo: ArrayList<BasicWeatherInfo>? = null,
    @SerializedName("main") val additionalWeatherInfo: AdditionalWeatherInfo? = null,
    @SerializedName("wind") val wind: Wind? = Wind(),
)