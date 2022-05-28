package com.accenture.beecycle.ui.main

import com.accenture.beecycle.domain.models.*

fun Data<Weather>.reduceWeather(): MainState {
    return when (responseType) {
        Status.SUCCESSFUL -> data?.let {
            MainState.ResultWeather(it)
        } ?: MainState.NoWeather
        Status.ERROR -> MainState.NoWeather
        Status.LOADING -> MainState.LoadingWeather
    }
}

fun Data<List<Bicycle>>.reduceUserBikes(): MainState {
    return MainState.ResultUserBicycles(data ?: emptyList())
}

fun Data<List<Team>>.reduceUserTeams(): MainState {
    return MainState.ResultUserTeams(data ?: emptyList())
}