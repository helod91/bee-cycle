package com.accenture.beecycle.data.repositories

import android.location.Geocoder
import com.accenture.beecycle.data.mappers.AddressMapper
import com.accenture.beecycle.domain.models.GeoSearchResult
import com.accenture.beecycle.domain.repositories.GeoLocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Single
class RemoteGeoLocationRepository(
    private val geocoder: Geocoder,
    private val addressMapper: AddressMapper
) : GeoLocationRepository {
    override suspend fun getGeoLocation(destination: String): List<GeoSearchResult> {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            withContext(Dispatchers.IO) {
                suspendCoroutine { continuation ->
                    geocoder.getFromLocationName(
                        destination,
                        15
                    ) { apiResult ->
                        continuation.resume(addressMapper.toSingleLineAddress(apiResult))
                    }
                }
            }
        } else {
            getLocation(destination)
        }
    }

    @Suppress("DEPRECATION")
    private fun getLocation(destination: String): List<GeoSearchResult> {
        return try {
            val apiResult = geocoder.getFromLocationName(
                destination, 15
            )
            addressMapper.toSingleLineAddress(apiResult.orEmpty())
        } catch (e: Exception) {
            emptyList()
        }
    }
}
