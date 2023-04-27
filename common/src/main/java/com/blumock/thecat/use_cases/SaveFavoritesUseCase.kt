package com.blumock.thecat.use_cases

import com.blumock.thecat.data.CatsItem

interface SaveFavoritesUseCase {

    suspend fun save(item: CatsItem)
}