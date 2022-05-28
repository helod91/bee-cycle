package com.accenture.beecycle.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivityMainBinding
import com.accenture.beecycle.ui.search.SearchActivity
import kotlinx.android.synthetic.main.item_bicycle_card.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity :
    BaseActivity<ActivityMainBinding, MainState, MainIntent, MainAction, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dispatchIntent(MainIntent.GetCurrentWeather(40.7741391,-74.3084179))
        iv_start_search.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
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
