package com.accenture.beecycle.data.di

import com.accenture.beecycle.domain.usecases.GetGeoLocations
import com.accenture.beecycle.domain.usecases.GetWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val useCaseModules = module {
    factory {
        GetWeather(get(), get())
    }
    factory {
        GetGeoLocations(get(), get())
    }
}
