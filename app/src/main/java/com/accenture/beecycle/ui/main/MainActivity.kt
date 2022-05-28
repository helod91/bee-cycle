package com.accenture.beecycle.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivityMainBinding
import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.RIDE_TYPE
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity :
    BaseActivity<ActivityMainBinding, MainState, MainIntent, MainAction, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    private val bicycleAdapter = BicycleAdapter()
    private val teamAdapter = TeamAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainBicycles.adapter = bicycleAdapter

        binding.mainTeams.adapter = teamAdapter
        binding.mainTeams.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        Glide.with(this)
            .load("https://imagez.tmz.com/image/bf/4by3/2022/04/05/bf0bbec74a1a463f96cf1bacfa831049_md.jpg")
            .circleCrop()
            .into(binding.mainAvatar)

        TabLayoutMediator(binding.mainBicyclesDots, binding.mainBicycles) { _, _ -> }.attach()
    }

    override fun onResume() {
        super.onResume()

        dispatchIntent(MainIntent.GetCurrentWeather(40.7741391, -74.3084179))
        dispatchIntent(MainIntent.GetUserBicycles)
        dispatchIntent(MainIntent.GetUserTeams)
    }

    override fun presentBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(this))

    override fun render(state: MainState) {
        when (state) {
            MainState.LoadingWeather -> binding.mainWeather.loading()
            MainState.NoWeather -> binding.mainWeather.loading()
            is MainState.ResultWeather -> binding.mainWeather.setWeather(state.weather)
            is MainState.ResultUserBicycles -> bicycleAdapter.data = ArrayList(state.bicycles).apply {
                add(Bicycle(rideType = RIDE_TYPE.ADD_BIKE))
            }
            is MainState.ResultUserTeams -> teamAdapter.data = state.teams
        }
    }
}
