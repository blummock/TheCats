package com.blumock.thecat.di

import android.content.Context
import com.blumock.domain.models.CatEntity
import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.usecases.UseCase
import java.io.File

interface AbstractUseCasesComponent {

    fun provideContext(): Context

    fun provideGetCatsUseCase(): UseCase<GetCatsArgs, Result<List<CatEntity>>>

    fun provideGetFavoritesUseCase(): UseCase<Unit, Result<List<FavoriteCatEntity>>>

    fun provideSaveToFavoritesUseCase(): UseCase<Pair<String, File>, Unit>

    fun provideDownloadImageUseCase(): UseCase<String, Unit>

    fun provideGetImageUseCase(): UseCase<String, File>
}