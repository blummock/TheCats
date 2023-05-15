package com.blumock.data.repo

import com.blumock.data.db.dao.FavoritesDaoRoom
import com.blumock.data.db.model.CatsEntityDb
import com.blumock.domain.models.FavoriteCatModel
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
            FavoriteCatModel(
                id = it.id,
                image = it.image
            )
        }.toList()

    override suspend fun save(item: FavoriteCatModel) = favoritesDao.insert(
        CatsEntityDb(
            id = item.id,
            image = item.image
        )
    )
}