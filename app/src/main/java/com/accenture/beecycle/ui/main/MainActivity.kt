package com.accenture.beecycle.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity :
    BaseActivity<ActivityMainBinding, MainState, MainIntent, MainAction, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dispatchIntent(MainIntent.GetCurrentWeather(40.7741391,-74.3084179))
    }

    override fun presentBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(this))

    override fun render(state: MainState) {
        when (state) {
            MainState.LoadingWeather -> binding.mainWeather.loading()
            MainState.NoWeather -> binding.mainWeather.loading()
            is MainState.ResultWeather -> binding.mainWeather.setWeather(state.weather)
        }
    }
}
