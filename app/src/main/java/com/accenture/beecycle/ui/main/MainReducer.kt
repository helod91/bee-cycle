package com.accenture.beecycle.ui.main

import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.Status
import com.accenture.beecycle.domain.models.Weather

fun Data<Weather>.reduce(): MainState {
    return when (responseType) {
        Status.SUCCESSFUL -> data?.let {
            MainState.ResultWeather(it)
        } ?: MainState.NoWeather
        Status.ERROR -> MainState.NoWeather
        Status.LOADING -> MainState.LoadingWeather
    }
}