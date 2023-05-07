package com.blumock.repository

import com.blumock.domain.models.CatEntity
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.repositories.CatsRepository
import com.blumock.network.NetApi
import com.blumock.network.check_connection.ConnectionChecker
import com.blumock.network.exceptions.NetworkException
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: NetApi,
    private val checker: ConnectionChecker,
) : CatsRepository {

    override suspend fun getCats(getCatsArgs: GetCatsArgs): List<CatEntity> {
        if (!checker.check()) throw NetworkException("Check your internet connection")
        return api.getCats(
            apiKey = getCatsArgs.apiKey,
            limit = getCatsArgs.limit,
            page = getCatsArgs.page,
            order = getCatsArgs.order,
            hasBreeds = getCatsArgs.hasBreeds,
            breedIds = getCatsArgs.breedIds,
            categoryIds = getCatsArgs.categoryIds,
            subId = getCatsArgs.subId
        )
            .map {
                CatEntity(
                    it.id,
                    it.url,
                    it.width,
                    it.height
                )
            }
    }
}