package com.blumock.thecat.use_cases

import com.blumock.thecat.data.CatsItem

interface FavoritesUseCase {

    suspend fun fetch(): Result<List<CatsItem>>
}