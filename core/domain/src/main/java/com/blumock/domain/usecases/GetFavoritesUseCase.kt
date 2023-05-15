package com.blumock.domain.usecases

import com.blumock.domain.models.FavoriteCatModel
import com.blumock.domain.repositories.FavoritesRepository

class GetFavoritesUseCase(private val favoritesRepository: FavoritesRepository) :
    UseCase<Unit, Result<@JvmSuppressWildcards List<FavoriteCatModel>>> {
    override suspend fun invoke(arg: Unit) = try {
        Result.success(favoritesRepository.getItems())
    } catch (e: Exception) {
        Result.failure(e)
    }
}