package com.blumock.domain.repositories

import com.blumock.domain.models.CatEntity
import com.blumock.domain.models.GetCatsArgs

interface CatsRepository {

    suspend fun getCats(getCatsArgs: GetCatsArgs): List<CatEntity>
}