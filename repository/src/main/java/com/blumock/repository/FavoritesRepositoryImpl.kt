package com.blumock.repository

import com.blumock.database.dao.FavoritesDaoRoom
import com.blumock.database.data.CatsEntityDb
import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDaoRoom
) : FavoritesRepository {

    override suspend fun getItems() = favoritesDao.selectCats()
        .asFlow()
        .map {
            FavoriteCatEntity(
                id = it.id,
                image = it.image
            )
        }.toList()

    override suspend fun save(item: FavoriteCatEntity) = favoritesDao.insert(
        CatsEntityDb(
            id = item.id,
            image = item.image
        )
    )
}