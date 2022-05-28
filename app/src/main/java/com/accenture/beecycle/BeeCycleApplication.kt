package com.accenture.beecycle

import android.app.Application
import com.accenture.beecycle.data.di.*
import com.accenture.beecycle.di.viewModelModules
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class BeeCycleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        ViewPump.init(ViewPump.builder()
            .addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath("abeezee_regular.ttf")
                        .build())
            )
            .build()
        )

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