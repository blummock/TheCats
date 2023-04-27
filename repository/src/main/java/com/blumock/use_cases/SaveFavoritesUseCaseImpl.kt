package com.blumock.use_cases

import com.blumock.thecat.repository.FavoritesRepository
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.use_cases.SaveFavoritesUseCase
import javax.inject.Inject

class SaveFavoritesUseCaseImpl @Inject constructor(private val repository: FavoritesRepository) : SaveFavoritesUseCase {

    override suspend fun save(item: CatsItem) = repository.save(item)
}