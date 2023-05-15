package com.blumock.domain.repositories

import com.blumock.domain.models.FavoriteCatModel

interface FavoritesRepository {

    suspend fun getItems(): List<FavoriteCatModel>

    suspend fun save(item: FavoriteCatModel)
}