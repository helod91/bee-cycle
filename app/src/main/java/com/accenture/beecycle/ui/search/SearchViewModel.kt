package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.BaseViewModel
import com.accenture.beecycle.domain.usecases.GetGeoLocations
import com.accenture.beecycle.domain.usecases.GetWeather
import com.accenture.beecycle.ui.main.MainAction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class SearchViewModel(
    private val getGeoLocation: GetGeoLocations,
    private val getWeatherUseCase: GetWeather
) : BaseViewModel<SearchState, SearchIntent, SearchAction>() {

    override fun intentToAction(intent: SearchIntent): SearchAction {
        return when (intent) {
            is SearchIntent.GetSearchResults -> SearchAction.GetSearchResult(intent.destination)
            is SearchIntent.GetCurrentWeather -> SearchAction.GetWeather(intent.latitude, intent.longitude)
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
                is SearchAction.GetWeather ->
                    getWeatherUseCase.execute(GetWeather.Params(action.latitude, action.longitude))
                        .collectLatest { result ->
                            result.data?.let { weather ->
                                state.value = SearchState.ResultWeather(weather)
                            }
                        }
            }
        }
    }

}
