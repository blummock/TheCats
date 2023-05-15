package com.blumock.data.repo

import com.blumock.data.network.NetApi
import com.blumock.data.network.exceptions.NetworkException
import com.blumock.domain.models.CatModel
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.repositories.CatsRepository
import com.blumock.domain.services.ConnectionChecker
import javax.inject.Inject

class CatsRepositoryImpl @Inject constructor(
    private val api: NetApi,
    private val checker: ConnectionChecker,
) : CatsRepository {

    override suspend fun getCats(getCatsArgs: GetCatsArgs): List<CatModel> {
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
                CatModel(
                    it.id,
                    it.url,
                    it.width,
                    it.height
                )
            }
    }
}