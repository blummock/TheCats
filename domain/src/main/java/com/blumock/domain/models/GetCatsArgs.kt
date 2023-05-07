package com.blumock.domain.models

data class GetCatsArgs(
    val apiKey: String = "REPLACE_ME",
    val limit: Int = 1,
    val page: Int = 0,
    val order: String = "ASC",
    val hasBreeds: Int = 0,
    val breedIds: String? = null,
    val categoryIds: String? = null,
    val subId: String? = null
)