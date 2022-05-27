package com.accenture.beecycle.data.mappers

import com.accenture.beecycle.data.entities.CurrentWeatherResponse
import com.accenture.beecycle.domain.models.Weather

class WeatherMapper {

    fun toWeather(currentWeatherResponse: CurrentWeatherResponse?): Weather {
        val basicWeatherInfo = try {
            currentWeatherResponse?.basicWeatherInfo?.get(0)
        } catch (e: Exception) {
            null
        }

        return Weather(
            basicWeatherInfo?.id,
            basicWeatherInfo?.main,
            basicWeatherInfo?.description,
            currentWeatherResponse?.additionalWeatherInfo?.temperature,
            currentWeatherResponse?.additionalWeatherInfo?.feelsLike,
            currentWeatherResponse?.additionalWeatherInfo?.tempMin,
            currentWeatherResponse?.additionalWeatherInfo?.tempMax,
            currentWeatherResponse?.additionalWeatherInfo?.humidity,
            currentWeatherResponse?.wind?.speed
        )
    }

}