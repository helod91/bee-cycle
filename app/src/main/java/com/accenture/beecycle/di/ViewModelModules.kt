package com.accenture.beecycle.di

import com.accenture.beecycle.ui.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModules = module {
    viewModel { MainViewModel() }
}
