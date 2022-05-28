package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.BaseViewModel
import com.accenture.beecycle.domain.usecases.GetGeoLocations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class SearchViewModel(
    private val getGeoLocation: GetGeoLocations
) : BaseViewModel<SearchState, SearchIntent, SearchAction>() {

    override fun intentToAction(intent: SearchIntent): SearchAction {
        return when (intent) {
            is SearchIntent.GetSearchResults -> SearchAction.GetSearchResult(intent.destination)
        }
    }

    override fun handleAction(action: SearchAction) {
        launchOnUI {
            when (action) {
                is SearchAction.GetSearchResult -> {
                    getGeoLocation.execute(GetGeoLocations.Params(action.results)).collectLatest { result ->
                        state.value = result.reduce()
                    }
                }
            }
        }
    }

}
