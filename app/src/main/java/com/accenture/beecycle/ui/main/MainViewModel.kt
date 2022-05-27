package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.BaseViewModel
import com.accenture.beecycle.domain.usecases.GetWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class MainViewModel(
    private val getWeather: GetWeather
) : BaseViewModel<MainState, MainIntent, MainAction>() {

    override fun intentToAction(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.GetCurrentWeather -> MainAction.GetWeather(
                intent.latitude,
                intent.longitude
            )
        }
    }

    override fun handleAction(action: MainAction) {
        launchOnUI {
            when (action) {
                is MainAction.GetWeather ->
                    getWeather.execute(GetWeather.Params(action.latitude, action.longitude))
                        .collectLatest { result ->
                            state.value = result.reduce()
                        }
            }
        }
    }
}
