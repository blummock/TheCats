package com.blumock.domain.repositories

import com.blumock.domain.models.FavoriteCatEntity

interface FavoritesRepository {

    suspend fun getItems(): List<FavoriteCatEntity>

    suspend fun save(item: FavoriteCatEntity)
}