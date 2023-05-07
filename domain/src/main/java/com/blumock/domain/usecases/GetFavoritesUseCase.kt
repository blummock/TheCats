package com.blumock.domain.usecases

import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    UseCase<Unit, Result<@JvmSuppressWildcards List<FavoriteCatEntity>>> {
    override suspend fun invoke(arg: Unit) = try {
        Result.success(favoritesRepository.getItems())
    } catch (e: Exception) {
        Result.failure(e)
    }
}