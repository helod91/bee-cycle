package com.accenture.beecycle.data.di

import com.accenture.beecycle.data.repositories.DeviceNetworkInfoProvider
import com.accenture.beecycle.data.repositories.RemoteWeatherRepository
import com.accenture.beecycle.domain.repositories.NetworkInfoProvider
import com.accenture.beecycle.domain.repositories.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val repositoryModules = module {

    single<NetworkInfoProvider> {
        DeviceNetworkInfoProvider(androidContext())
    }

    single<WeatherRepository> {
        RemoteWeatherRepository()
    }
}