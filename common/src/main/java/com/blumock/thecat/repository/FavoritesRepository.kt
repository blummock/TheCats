package com.blumock.thecat.repository

import com.blumock.thecat.data.CatsItem

interface FavoritesRepository {

    suspend fun getItems(): List<CatsItem>

    suspend fun save(item: CatsItem)
}