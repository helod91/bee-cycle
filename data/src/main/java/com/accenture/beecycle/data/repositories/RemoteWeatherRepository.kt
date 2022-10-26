package com.accenture.beecycle.data.repositories

import com.accenture.beecycle.data.apiservices.WEATHER_API_KEY
import com.accenture.beecycle.data.apiservices.WeatherApiService
import com.accenture.beecycle.data.mappers.WeatherMapper
import com.accenture.beecycle.domain.models.Weather
import com.accenture.beecycle.domain.repositories.WeatherRepository
import org.koin.core.annotation.Single

private const val WEATHER_UNITS = "metric"

@Single
class RemoteWeatherRepository(
    private val weatherApiService: WeatherApiService,
    private val weatherMapper: WeatherMapper
) : WeatherRepository {

    override suspend fun getWeather(latitude: Double?, longitude: Double?): Weather {
        val apiResult = weatherApiService.getCurrentWeather(
            latitude,
            longitude,
            WEATHER_UNITS,
            WEATHER_API_KEY
        )

        return weatherMapper.toWeather(apiResult)
    }
}
