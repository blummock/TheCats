package com.blumock.data.network

import com.blumock.data.network.model.CatsEntity
import retrofit2.http.GET
import retrofit2.http.Query


interface NetApi {

    @GET("/v1/images/search?")
    suspend fun getCats(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("order") order: String,
        @Query("has_breeds") hasBreeds: Int,
        @Query("breed_ids") breedIds: String?,
        @Query("category_ids") categoryIds: String?,
        @Query("sub_id") subId: String?
    ): List<CatsEntity>
}
