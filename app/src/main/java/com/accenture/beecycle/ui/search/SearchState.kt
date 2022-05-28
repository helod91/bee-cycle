package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.ViewState
import com.accenture.beecycle.domain.models.GeoSearchResult
import com.accenture.beecycle.domain.models.Weather
import com.accenture.beecycle.domain.models.Vehicle
import com.accenture.beecycle.ui.main.MainState

sealed class SearchState : ViewState {
    object LoadingSearchResult : SearchState()
    object NoSearchResult : SearchState()
    data class ResultSearch(val results: List<GeoSearchResult>): SearchState()
    data class ResultWeather(val weather: Weather) : SearchState()
//    data class ResultVehicleTrips(val vehicles: List<Vehicle>) : SearchState()
}
