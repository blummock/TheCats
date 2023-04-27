package com.blumock.thecat.repository

import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.use_cases.FetchParams

interface CatsRepository {

    suspend fun getCats(fetchParams: FetchParams): List<CatsItem>
}