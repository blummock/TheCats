package com.blumock.domain.usecases

import com.blumock.domain.models.CatModel
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.repositories.CatsRepository

class GetCatsUseCase(private val catsRepository: CatsRepository) :
    UseCase<GetCatsArgs, Result<@JvmSuppressWildcards List<CatModel>>> {
    override suspend fun invoke(arg: GetCatsArgs) = kotlin.runCatching {
        catsRepository.getCats(arg)
    }
}