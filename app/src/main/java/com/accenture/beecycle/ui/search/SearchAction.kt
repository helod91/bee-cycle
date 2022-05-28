package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.ViewAction

sealed class SearchAction : ViewAction {
    data class GetSearchResult(val results: String) : SearchAction()
    data class GetWeather(val latitude: Double?, val longitude: Double?): SearchAction()
//    object GetVehicleTrips : SearchAction()
}
