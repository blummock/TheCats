package com.blumock.domain.usecases

import com.blumock.domain.models.CatEntity
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.repositories.CatsRepository
import javax.inject.Inject

class GetCatsUseCase @Inject constructor(private val catsRepository: CatsRepository) :
    UseCase<GetCatsArgs, Result<@JvmSuppressWildcards List<CatEntity>>> {
    override suspend fun invoke(arg: GetCatsArgs) = kotlin.runCatching {
        catsRepository.getCats(arg)
    }
}