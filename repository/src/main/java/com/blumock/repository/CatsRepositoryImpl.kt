package com.blumock.repository

import com.blumock.network.NetApi
import com.blumock.network.check_connection.ConnectionChecker
import com.blumock.network.exceptions.NetworkException
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.image.ImageLoader
import com.blumock.thecat.repository.CatsRepository
import com.blumock.thecat.repository.FavoritesRepository
import com.blumock.thecat.use_cases.FetchParams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: NetApi,
    private val checker: ConnectionChecker,
    private val imageLoader: ImageLoader,
    private val favoritesRepository: FavoritesRepository,
) : CatsRepository {

    override suspend fun getCats(fetchParams: FetchParams): List<CatsItem> {
        if (!checker.check()) throw NetworkException("Check your internet connection")
        val favorite = favoritesRepository.getItems()
        val merged = api.getCats(
            apiKey = fetchParams.apiKey,
            limit = fetchParams.limit,
            page = fetchParams.page,
            order = fetchParams.order,
            hasBreeds = fetchParams.hasBreeds,
            breedIds = fetchParams.breedIds,
            categoryIds = fetchParams.categoryIds,
            subId = fetchParams.subId
        )
            .asFlow()
            .map {
                CatsItem(
                    id = it.id,
                    url = it.url,
                    with = it.with,
                    height = it.height
                )
            }
            .flowOn(Dispatchers.IO)
            .map { item -> favorite.find { it.id == item.id } ?: item }
            .toList()
        imageLoader.load(merged)
        return merged
    }
}