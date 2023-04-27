package com.blumock.network

import com.blumock.network.data.CatsEntity
import com.blumock.thecat.use_cases.Order
import retrofit2.http.GET
import retrofit2.http.Query


interface NetApi {

    @GET("/v1/images/search?")
    suspend fun getCats(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: Order,
        @Query("has_breeds") hasBreeds: Int,
        @Query("breed_ids") breedIds: String?,
        @Query("category_ids") categoryIds: String?,
        @Query("sub_id") subId: String?
    ): List<CatsEntity>
}
