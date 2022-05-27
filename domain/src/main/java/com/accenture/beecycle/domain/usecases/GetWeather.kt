package com.accenture.beecycle.domain.usecases

import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.Weather
import com.accenture.beecycle.domain.models.error.DomainErrorCode
import com.accenture.beecycle.domain.models.error.DomainException
import com.accenture.beecycle.domain.repositories.NetworkInfoProvider
import com.accenture.beecycle.domain.repositories.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class GetWeather(
    private val weatherRepository: WeatherRepository,
    private val networkInfoProvider: NetworkInfoProvider
) : UseCase<GetWeather.Params, Weather>() {

    override suspend fun provideData(params: Params): Data<Weather> {
        if (networkInfoProvider.hasInternetConnection().not()) {
            throw DomainException(DomainErrorCode.NO_INTERNET_CONNECTION)
        }

        return Data.success(weatherRepository.getWeather())
    }

    data class Params(val nothing: Nothing)
}