package com.accenture.beecycle

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.accenture.beecycle.data.di.*
import com.accenture.beecycle.di.AppModule
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import org.koin.ksp.generated.*

@ExperimentalCoroutinesApi
class BeeCycleApplication : Application() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate() {
        super.onCreate()

        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("abeezee_regular.ttf")
                            .build()
                    )
                )
                .build()
        )

        startKoin {
            androidLogger()
            androidContext(this@BeeCycleApplication)
            modules(listOf(
                apiModules,
                mapperModules,
                networkModules,
                repositoryModules,
                useCaseModules,
                AppModule().module
            ))
        }
    }
}
