package com.accenture.beecycle.data.di

import com.accenture.beecycle.data.mappers.AddressMapper
import com.accenture.beecycle.data.mappers.WeatherMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val mapperModules = module {
    singleOf(::WeatherMapper)
    singleOf(::AddressMapper)
}
