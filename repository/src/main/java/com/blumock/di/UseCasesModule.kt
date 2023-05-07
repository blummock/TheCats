package com.blumock.di

import com.blumock.domain.models.CatEntity
import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.usecases.*
import com.blumock.thecat.di.scope.ViewModelScope
import dagger.Binds
import dagger.Module
import java.io.File

@Module
interface UseCasesModule {

    @Binds
    @ViewModelScope
    fun provideGetCatsUseCase(useCase: GetCatsUseCase): UseCase<GetCatsArgs, Result<List<CatEntity>>>

    @Binds
    @ViewModelScope
    fun provideGetFavoritesUseCase(useCase: GetFavoritesUseCase): UseCase<Unit, Result<List<FavoriteCatEntity>>>

    @Binds
    @ViewModelScope
    fun provideSaveFavoritesUseCase(useCase: SaveToFavoritesUseCase): UseCase<Pair<String, File>, Unit>

    @Binds
    @ViewModelScope
    fun provideDownloadImageUseCase(useCase: DownloadImageUseCase): UseCase<String, Unit>

    @Binds
    @ViewModelScope
    fun provideGetImageUseCase(useCase: DownloadPreviewUseCase): UseCase<String, File>
}