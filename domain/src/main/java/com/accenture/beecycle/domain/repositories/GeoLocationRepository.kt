package com.accenture.beecycle.domain.repositories

import com.accenture.beecycle.domain.models.GeoSearchResult

interface GeoLocationRepository {
    suspend fun getGeoLocation(destination: String): List<GeoSearchResult>
}
