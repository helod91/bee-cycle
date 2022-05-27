package com.accenture.beecycle.ui.main

import android.view.LayoutInflater
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class MainActivity :
    BaseActivity<ActivityMainBinding, MainState, MainIntent, MainAction, MainViewModel>() {

    override val viewModel: MainViewModel by viewModel()

    override fun presentBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(LayoutInflater.from(this))

    override fun render(state: MainState) {

    }
}
