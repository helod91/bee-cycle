package com.accenture.beecycle.data.di

import com.accenture.beecycle.data.apiservices.WEATHER_API_BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val WEATHER_API = "weather-api"

val networkModules = module {

    singleOf<OkHttpClient> {
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        okHttpBuilder.build()
    }

    singleOf<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<Retrofit>(named(WEATHER_API)) {
        Retrofit.Builder()
            .baseUrl(WEATHER_API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}
