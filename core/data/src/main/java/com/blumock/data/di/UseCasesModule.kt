package com.blumock.data.di

import com.blumock.api.di.FragmentScope
import com.blumock.domain.models.CatModel
import com.blumock.domain.models.FavoriteCatModel
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.repositories.CatsRepository
import com.blumock.domain.repositories.FavoritesRepository
import com.blumock.domain.services.CreateFileService
import com.blumock.domain.services.DownloadImageService
import com.blumock.domain.services.DownloadPreviewService
import com.blumock.domain.usecases.*
import dagger.Module
import dagger.Provides
import java.io.File

@Module
class UseCasesModule {

    @Provides
    @FragmentScope
    fun provideGetFavoritesUseCase(
        favoritesRepository: FavoritesRepository
    ): UseCase<Unit, Result<List<FavoriteCatModel>>> = GetFavoritesUseCase(favoritesRepository)

    @Provides
    @FragmentScope
    fun provideGetCatsUseCase(
        favoritesRepository: FavoritesRepository,
        catsRepository: CatsRepository
    ): UseCase<GetCatsArgs, Result<List<CatModel>>> = GetCatsUseCase(catsRepository, favoritesRepository)

    @Provides
    @FragmentScope
    fun provideSaveFavoritesUseCase(
        favoritesRepository: FavoritesRepository, createFileService: CreateFileService
    ): UseCase<Pair<String, File>, Unit> = SaveToFavoritesUseCase(favoritesRepository, createFileService)

    @Provides
    @FragmentScope
    fun provideDownloadImageUseCase(downloadImageService: DownloadImageService): UseCase<String, Unit> =
        DownloadImageUseCase(downloadImageService)

    @Provides
    @FragmentScope
    fun provideGetImageUseCase(downloadPreviewService: DownloadPreviewService): UseCase<String, File> =
        DownloadPreviewUseCase(downloadPreviewService)
}