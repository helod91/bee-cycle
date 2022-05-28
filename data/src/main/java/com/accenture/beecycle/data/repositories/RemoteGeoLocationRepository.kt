package com.accenture.beecycle.data.repositories

import android.location.Geocoder
import com.accenture.beecycle.data.mappers.AddressMapper
import com.accenture.beecycle.domain.models.GeoSearchResult
import com.accenture.beecycle.domain.repositories.GeoLocationRepository

class RemoteGeoLocationRepository(
    private val geocoder: Geocoder,
    private val addressMapper: AddressMapper
) : GeoLocationRepository {
    override suspend fun getGeoLocation(destination: String): List<GeoSearchResult> {
        return try {
            val apiResult = geocoder.getFromLocationName(destination, 15)

            addressMapper.toSingleLineAddress(apiResult)
        } catch (e: Exception) {
            emptyList()
        }
    }
}
