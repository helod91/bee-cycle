package com.accenture.beecycle.data.di

import com.accenture.beecycle.data.apiservices.WeatherApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModules = module {
    single<WeatherApiService> {
        get<Retrofit>(named(WEATHER_API)).create(WeatherApiService::class.java)
    }
}