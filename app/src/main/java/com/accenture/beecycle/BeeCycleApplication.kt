package com.accenture.beecycle

import android.app.Application
import com.accenture.beecycle.data.di.*
import com.accenture.beecycle.di.viewModelModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class BeeCycleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BeeCycleApplication)
            modules(listOf(
                apiModules,
                mapperModules,
                networkModules,
                repositoryModules,
                useCaseModules,
                viewModelModules
            ))
        }
    }
}