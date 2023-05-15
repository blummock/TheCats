package com.blumock.domain.usecases

import com.blumock.domain.models.CatModel
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.repositories.CatsRepository
import com.blumock.domain.repositories.FavoritesRepository

class GetCatsUseCase(
    private val catsRepository: CatsRepository,
    private val favoritesRepository: FavoritesRepository,
) :
    UseCase<GetCatsArgs, Result<@JvmSuppressWildcards List<CatModel>>> {
    override suspend fun invoke(arg: GetCatsArgs) = kotlin.runCatching {
        val favorites = favoritesRepository.getItems()
        catsRepository.getCats(arg)
            .map { if (favorites.any { favorite -> favorite.id == it.id }) it.copy(isFavorite = true) else it }
    }
}