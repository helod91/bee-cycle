package com.accenture.beecycle.domain.usecases

import com.accenture.beecycle.domain.models.Data
import com.accenture.beecycle.domain.models.error.DomainException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
abstract class UseCase<Params, Result> {

    protected abstract suspend fun provideData(params: Params): Data<Result>

    fun execute(params: Params): Flow<Data<Result>> {
        return flow {
            emit(provideData(params))
        }.onStart {
            emit(Data.loading())
        }.catch { exception ->
            emit(Data.error(DomainException.parseThrowable(exception)))
        }
    }
}