package com.blumock.use_cases

import android.util.Log
import com.blumock.thecat.repository.FavoritesRepository
import com.blumock.thecat.use_cases.FavoritesUseCase
import javax.inject.Inject

class FavoritesUseCaseImpl @Inject constructor(private val favoritesRepository: FavoritesRepository) :
    FavoritesUseCase {
    override suspend fun fetch() = try {
        Result.success(favoritesRepository.getItems())
    } catch (e: Exception) {
        Log.d(this.javaClass.simpleName, e.toString())
        Result.failure(e)
    }
}