package com.blumock.di

import com.blumock.thecat.di.scope.ViewModelScope
import com.blumock.thecat.use_cases.CatsUseCase
import com.blumock.thecat.use_cases.FavoritesUseCase
import com.blumock.thecat.use_cases.SaveFavoritesUseCase
import com.blumock.use_cases.CatsUseCaseImpl
import com.blumock.use_cases.FavoritesUseCaseImpl
import com.blumock.use_cases.SaveFavoritesUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCasesModule {

    @Binds
    @ViewModelScope
    fun provideCatsUseCase(useCase: CatsUseCaseImpl): CatsUseCase

    @Binds
    @ViewModelScope
    fun provideFavoritesUseCase(useCase: FavoritesUseCaseImpl): FavoritesUseCase

    @Binds
    @ViewModelScope
    fun provideSaveFavoritesUseCase(useCase: SaveFavoritesUseCaseImpl): SaveFavoritesUseCase
}