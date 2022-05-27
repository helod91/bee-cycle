package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.ViewIntent

sealed class MainIntent : ViewIntent {
    data class GetCurrentWeather(val latitude: Double?, val longitude: Double?) : MainIntent()
}
