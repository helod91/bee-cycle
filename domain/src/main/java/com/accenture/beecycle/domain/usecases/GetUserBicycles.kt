package com.accenture.beecycle.domain.usecases

import com.accenture.beecycle.domain.models.Bicycle
import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.error.DomainErrorCode
import com.accenture.beecycle.domain.models.error.DomainException
import com.accenture.beecycle.domain.repositories.BicycleRepository
import com.accenture.beecycle.domain.repositories.NetworkInfoProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.annotation.Factory

@ExperimentalCoroutinesApi
@Factory
class GetUserBicycles(
    private val bicycleRepository: BicycleRepository,
    private val networkInfoProvider: NetworkInfoProvider
) : UseCase<GetUserBicycles.Params, List<Bicycle>>() {

    override suspend fun provideData(params: Params): Data<List<Bicycle>> {
        if (networkInfoProvider.hasInternetConnection().not()) {
            throw DomainException(DomainErrorCode.NO_INTERNET_CONNECTION)
        }

        return Data.success(bicycleRepository.getUserBicycles())
    }

    class Params
}
