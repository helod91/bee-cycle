package com.accenture.beecycle.data.apiservices

import com.accenture.beecycle.data.entities.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val WEATHER_API_BASE_URL = "https://api.openweathermap.org/"
const val WEATHER_API_KEY = "f578c422b3ccb4fc52cf38335cc4f812"

interface WeatherApiService {

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(@Query("lat") latitude: Double?, @Query("lon") longitude: Double?, @Query("units") units: String?, @Query("appid") appId: String?): CurrentWeatherResponse
}
