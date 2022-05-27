package com.accenture.beecycle.data.di

import com.accenture.beecycle.data.mappers.WeatherMapper
import org.koin.dsl.module

val mapperModules = module {
    single {
        WeatherMapper()
    }
}