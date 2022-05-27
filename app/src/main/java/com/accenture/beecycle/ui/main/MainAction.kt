package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.ViewAction

sealed class MainAction : ViewAction {
    data class GetWeather(val latitude: Double?, val longitude: Double?): MainAction()
}
