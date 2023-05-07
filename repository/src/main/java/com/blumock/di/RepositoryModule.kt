package com.blumock.di

import com.blumock.domain.repositories.CatsRepository
import com.blumock.domain.repositories.FavoritesRepository
import com.blumock.domain.services.CreateFileService
import com.blumock.domain.services.DownloadImageService
import com.blumock.domain.services.DownloadPreviewService
import com.blumock.network.CreateFileServiceImpl
import com.blumock.network.DownloadImageServiceImpl
import com.blumock.network.DownloadPreviewPreviewServiceImpl
import com.blumock.network.check_connection.ConnectionChecker
import com.blumock.network.check_connection.ConnectionCheckerImpl
import com.blumock.repository.CatsRepositoryImpl
import com.blumock.repository.FavoritesRepositoryImpl
import com.blumock.thecat.di.scope.ViewModelScope
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @ViewModelScope
    fun provideConnectionChecker(connectionChecker: ConnectionCheckerImpl): ConnectionChecker

    @Binds
    @ViewModelScope
    fun provideImageStore(createFileService: CreateFileServiceImpl): CreateFileService

    @Binds
    @ViewModelScope
    fun provideCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository

    @Binds
    @ViewModelScope
    fun provideFavoritesRepository(catsRepository: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    @ViewModelScope
    fun provideDownloadImageService(downloadImageService: DownloadImageServiceImpl): DownloadImageService

    @Binds
    @ViewModelScope
    fun provideGetImageService(getImageService: DownloadPreviewPreviewServiceImpl): DownloadPreviewService
}