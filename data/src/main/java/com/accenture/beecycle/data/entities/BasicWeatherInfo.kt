package com.accenture.beecycle.data.entities

import com.google.gson.annotations.SerializedName

data class BasicWeatherInfo(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("main") val main: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("icon") val icon: String? = null
)