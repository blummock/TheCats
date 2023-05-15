package com.blumock.domain.repositories

import com.blumock.domain.models.CatModel
import com.blumock.domain.models.GetCatsArgs

interface CatsRepository {

    suspend fun getCats(getCatsArgs: GetCatsArgs): List<CatModel>
}