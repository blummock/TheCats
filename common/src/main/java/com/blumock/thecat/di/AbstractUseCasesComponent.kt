package com.blumock.thecat.di

import com.blumock.thecat.use_cases.CatsUseCase
import com.blumock.thecat.use_cases.FavoritesUseCase
import com.blumock.thecat.use_cases.SaveFavoritesUseCase

interface AbstractUseCasesComponent {

    fun provideCatsUseCase(): CatsUseCase

    fun provideFavoritesUseCase(): FavoritesUseCase

    fun provideSaveFavoritesUseCase(): SaveFavoritesUseCase
}