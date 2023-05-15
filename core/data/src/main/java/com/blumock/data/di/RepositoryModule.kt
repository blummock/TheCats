package com.blumock.data.di

import com.blumock.data.repo.CatsRepositoryImpl
import com.blumock.data.repo.FavoritesRepositoryImpl
import com.blumock.domain.repositories.CatsRepository
import com.blumock.domain.repositories.FavoritesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository

    @Binds
    @Singleton
    fun provideFavoritesRepository(catsRepository: FavoritesRepositoryImpl): FavoritesRepository
}