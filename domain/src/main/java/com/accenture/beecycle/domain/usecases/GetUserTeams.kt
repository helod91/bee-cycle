package com.accenture.beecycle.domain.usecases

import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.Team
import com.accenture.beecycle.domain.models.error.DomainErrorCode
import com.accenture.beecycle.domain.models.error.DomainException
import com.accenture.beecycle.domain.repositories.BicycleRepository
import com.accenture.beecycle.domain.repositories.NetworkInfoProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.annotation.Factory

@ExperimentalCoroutinesApi
@Factory
class GetUserTeams(
    private val bicycleRepository: BicycleRepository,
    private val networkInfoProvider: NetworkInfoProvider
) : UseCase<GetUserTeams.Param, List<Team>>() {

    override suspend fun provideData(params: Param): Data<List<Team>> {
        if (networkInfoProvider.hasInternetConnection().not()) {
            throw DomainException(DomainErrorCode.NO_INTERNET_CONNECTION)
        }

        return Data.success(bicycleRepository.getTeams())
    }

    class Param
}
