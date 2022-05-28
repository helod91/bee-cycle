package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.ViewState
import com.accenture.beecycle.domain.models.GeoSearchResult

sealed class SearchState : ViewState {
    object LoadingSearchResult : SearchState()
    object NoSearchResult : SearchState()
    data class ResultSearch(val results: List<GeoSearchResult>): SearchState()
}
