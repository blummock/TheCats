package com.blumock.repository

import android.graphics.BitmapFactory
import com.blumock.database.dao.FavoritesDaoRoom
import com.blumock.database.data.CatsEntityDb
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.image.ImageStore
import com.blumock.thecat.repository.FavoritesRepository
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val favoritesDao: FavoritesDaoRoom,
    private val imageStore: ImageStore
) : FavoritesRepository {

    override suspend fun getItems() = favoritesDao.selectCats()
        .asFlow()
        .map {
            CatsItem(
                id = it.id,
                url = it.url,
                with = it.with,
                height = it.height,
                isFav = true,
                image = BitmapFactory.decodeFile(it.image)
            )
        }.toList()

    override suspend fun save(item: CatsItem) = favoritesDao.insert(
        CatsEntityDb(
            id = item.id,
            url = item.url,
            with = item.with,
            height = item.height,
            image = imageStore.putImg(item.image ?: throw NullPointerException("Save null image not allowed!"))
        )
    )
}