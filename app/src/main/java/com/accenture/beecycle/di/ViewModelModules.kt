package com.accenture.beecycle.di

import com.accenture.beecycle.ui.main.MainViewModel
import com.accenture.beecycle.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModules = module {
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { SearchViewModel() }
}
