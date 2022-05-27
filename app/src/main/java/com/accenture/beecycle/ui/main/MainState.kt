package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.ViewState
import com.accenture.beecycle.domain.models.Weather

sealed class MainState : ViewState {
    object LoadingWeather : MainState()
    object NoWeather : MainState()
    data class ResultWeather(val weather: Weather?): MainState()
}
