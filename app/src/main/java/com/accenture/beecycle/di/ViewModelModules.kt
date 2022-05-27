package com.accenture.beecycle.di

import com.accenture.beecycle.ui.splash.SplashViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModules = module {
    viewModel { SplashViewModel() }
}