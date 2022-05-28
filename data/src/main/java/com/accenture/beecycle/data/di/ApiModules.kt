package com.accenture.beecycle.data.di

import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresApi
import com.accenture.beecycle.data.apiservices.WeatherApiService
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

@RequiresApi(Build.VERSION_CODES.N)
val apiModules = module {
    single<WeatherApiService> {
        get<Retrofit>(named(WEATHER_API)).create(WeatherApiService::class.java)
    }

    single {
        Geocoder(androidContext(), androidContext().resources.configuration.locales.get(0))
    }
}
