package com.accenture.beecycle.data.di

import com.accenture.beecycle.domain.usecases.GetGeoLocations
import com.accenture.beecycle.domain.usecases.GetUserBicycles
import com.accenture.beecycle.domain.usecases.GetUserTeams
import com.accenture.beecycle.domain.usecases.GetWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val useCaseModules = module {
    factoryOf(::GetGeoLocations) bind (GetGeoLocations::class)
    factoryOf(::GetUserBicycles) bind (GetUserBicycles::class)
    factoryOf(::GetUserTeams) bind (GetUserTeams::class)
    factoryOf(::GetWeather) bind (GetWeather::class)
}
