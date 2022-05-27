package com.accenture.beecycle.domain.repositories

interface NetworkInfoProvider {
    fun hasInternetConnection(): Boolean
}