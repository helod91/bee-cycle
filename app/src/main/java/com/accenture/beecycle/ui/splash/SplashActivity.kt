package com.accenture.beecycle.ui.splash

import android.view.LayoutInflater
import com.accenture.beecycle.common.BaseActivity
import com.accenture.beecycle.databinding.ActivitySplashBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SplashActivity :
    BaseActivity<ActivitySplashBinding, SplashState, SplashIntent, SplashAction, SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModel()

    override fun presentBinding(): ActivitySplashBinding =
        ActivitySplashBinding.inflate(LayoutInflater.from(this))

    override fun render(state: SplashState) {

    }
}