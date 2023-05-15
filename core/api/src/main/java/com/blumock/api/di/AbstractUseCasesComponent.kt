package com.blumock.api.di

import com.blumock.domain.models.CatModel
import com.blumock.domain.models.FavoriteCatModel
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.usecases.UseCase
import java.io.File

interface AbstractUseCasesComponent {

    fun provideGetCatsUseCase(): UseCase<GetCatsArgs, Result<List<CatModel>>>

    fun provideGetFavoritesUseCase(): UseCase<Unit, Result<List<FavoriteCatModel>>>

    fun provideSaveToFavoritesUseCase(): UseCase<Pair<String, File>, Unit>

    fun provideDownloadImageUseCase(): UseCase<String, Unit>

    fun provideGetImageUseCase(): UseCase<String, File>
}