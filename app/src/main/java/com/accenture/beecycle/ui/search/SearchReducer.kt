package com.accenture.beecycle.ui.search

import com.accenture.beecycle.domain.models.GeoSearchResult
import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.Status

fun Data<List<GeoSearchResult>>.reduce(): SearchState {
    return when (responseType){
        Status.SUCCESSFUL -> data?.let {
            SearchState.ResultSearch(it)
        } ?: SearchState.NoSearchResult
        Status.ERROR -> SearchState.NoSearchResult
        Status.LOADING -> SearchState.LoadingSearchResult
    }
}
