package com.blumock.api.di

import com.blumock.domain.repositories.CatsRepository
import com.blumock.domain.repositories.FavoritesRepository

interface AbstractRepositoryComponent : AbstractPlatformComponent {

    fun provideCatsRepository(): CatsRepository

    fun provideFavoritesRepository(): FavoritesRepository

}