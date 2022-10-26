package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.BaseViewModel
import com.accenture.beecycle.domain.usecases.GetUserTeams
import com.accenture.beecycle.domain.usecases.GetUserBicycles
import com.accenture.beecycle.domain.usecases.GetWeather
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.annotation.KoinViewModel

@KoinViewModel(binds = [MainViewModel::class])
@ExperimentalCoroutinesApi
class MainViewModel constructor(
    private val getWeather: GetWeather,
    private val getUserBicycles: GetUserBicycles,
    private val getUserTeams: GetUserTeams
) : BaseViewModel<MainState, MainIntent, MainAction>() {

    override fun intentToAction(intent: MainIntent): MainAction {
        return when (intent) {
            is MainIntent.GetCurrentWeather -> MainAction.GetWeather(
                intent.latitude,
                intent.longitude
            )
            MainIntent.GetUserBicycles -> MainAction.GetUserBicycles
            MainIntent.GetUserTeams -> MainAction.GetUserTeams
        }
    }

    override fun handleAction(action: MainAction) {
        launchOnUI {
            when (action) {
                is MainAction.GetWeather ->
                    getWeather.execute(GetWeather.Params(action.latitude, action.longitude))
                        .collectLatest { result ->
                            state.value = result.reduceWeather()
                        }
                MainAction.GetUserBicycles ->
                    getUserBicycles.execute(GetUserBicycles.Params())
                        .collectLatest { result ->
                            state.value = result.reduceUserBikes()
                        }
                MainAction.GetUserTeams ->
                    getUserTeams.execute(GetUserTeams.Param())
                        .collectLatest { result ->
                            state.value = result.reduceUserTeams()
                        }
            }
        }
    }
}
