package com.accenture.beecycle.data.repositories

import com.accenture.beecycle.domain.models.Weather
import com.accenture.beecycle.domain.repositories.WeatherRepository

class RemoteWeatherRepository : WeatherRepository {

    override suspend fun getWeather(): Weather {
        TODO("Not yet implemented")
    }
}