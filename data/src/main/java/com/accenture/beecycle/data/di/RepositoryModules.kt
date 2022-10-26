package com.accenture.beecycle.data.di

import com.accenture.beecycle.data.repositories.DeviceNetworkInfoProvider
import com.accenture.beecycle.data.repositories.RemoteBicycleRepository
import com.accenture.beecycle.data.repositories.RemoteGeoLocationRepository
import com.accenture.beecycle.data.repositories.RemoteWeatherRepository
import com.accenture.beecycle.domain.repositories.BicycleRepository
import com.accenture.beecycle.domain.repositories.GeoLocationRepository
import com.accenture.beecycle.domain.repositories.NetworkInfoProvider
import com.accenture.beecycle.domain.repositories.WeatherRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModules = module {
    singleOf(::DeviceNetworkInfoProvider) { bind<NetworkInfoProvider>() }
    singleOf(::RemoteWeatherRepository) { bind<WeatherRepository>() }
    singleOf(::RemoteGeoLocationRepository) { bind<GeoLocationRepository>() }
    singleOf(::RemoteBicycleRepository) { bind<BicycleRepository>() }
}
