package com.accenture.beecycle.domain.usecases

import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.GeoSearchResult
import com.accenture.beecycle.domain.models.error.DomainErrorCode
import com.accenture.beecycle.domain.models.error.DomainException
import com.accenture.beecycle.domain.repositories.GeoLocationRepository
import com.accenture.beecycle.domain.repositories.NetworkInfoProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetGeoLocations(
    private val geoLocationRepository: GeoLocationRepository,
    private val networkInfoProvider: NetworkInfoProvider
) : UseCase<GetGeoLocations.Params, List<GeoSearchResult>>() {

    override suspend fun provideData(params: Params): Data<List<GeoSearchResult>> {
        if (networkInfoProvider.hasInternetConnection().not()) {
            throw DomainException(DomainErrorCode.NO_INTERNET_CONNECTION)
        }

        return Data.success(geoLocationRepository.getGeoLocation(params.destination))
    }

    data class Params(val destination: String)
}
