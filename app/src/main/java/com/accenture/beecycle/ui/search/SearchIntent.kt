package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.ViewIntent

sealed class SearchIntent : ViewIntent {
    data class GetSearchResults(val destination: String) : SearchIntent()
    data class GetCurrentWeather(val latitude: Double?, val longitude: Double?) : SearchIntent()
}
