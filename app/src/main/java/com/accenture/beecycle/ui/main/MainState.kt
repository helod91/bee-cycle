package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.ViewState
import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.Team
import com.accenture.beecycle.domain.models.Weather

sealed class MainState : ViewState {
    object LoadingWeather : MainState()
    object NoWeather : MainState()
    data class ResultWeather(val weather: Weather?): MainState()
    data class ResultUserBicycles(val bicycles : List<Bicycle>) : MainState()
    data class ResultUserTeams(val teams: List<Team>): MainState()
}
