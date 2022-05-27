package com.accenture.beecycle.domain.repositories

import com.accenture.beecycle.domain.models.Weather

interface WeatherRepository {

    suspend fun getWeather(latitude: Double?, longitude: Double?): Weather
}