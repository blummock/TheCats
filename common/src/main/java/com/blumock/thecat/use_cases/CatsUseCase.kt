package com.blumock.thecat.use_cases

import com.blumock.thecat.data.CatsItem

interface CatsUseCase {

    suspend fun fetch(fetchParams: FetchParams): Result<List<CatsItem>>
}

data class FetchParams(
    val apiKey: String = "REPLACE_ME",
    val limit: Int = 1,
    val page: Int = 0,
    val order: Order = Order.ASC,
    val hasBreeds: Int = 0,
    val breedIds: String? = null,
    val categoryIds: String? = null,
    val subId: String? = null
)

enum class Order {
    RAND,
    DESC,
    ASC
}