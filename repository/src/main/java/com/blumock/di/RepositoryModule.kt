package com.blumock.di

import com.blumock.database.images.ImageStoreImpl
import com.blumock.network.check_connection.ConnectionChecker
import com.blumock.network.check_connection.ConnectionCheckerImpl
import com.blumock.network.images.ImageLoaderImpl
import com.blumock.thecat.repository.CatsRepository
import com.blumock.repository.CatsRepositoryImpl
import com.blumock.thecat.repository.FavoritesRepository
import com.blumock.repository.FavoritesRepositoryImpl
import com.blumock.thecat.di.scope.ViewModelScope
import com.blumock.thecat.image.ImageLoader
import com.blumock.thecat.image.ImageStore
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @ViewModelScope
    fun provideConnectionChecker(connectionChecker: ConnectionCheckerImpl): ConnectionChecker

    @Binds
    @ViewModelScope
    fun provideImageLoader(imageLoader: ImageLoaderImpl): ImageLoader

    @Binds
    @ViewModelScope
    fun provideImageStore(imageStore: ImageStoreImpl): ImageStore

    @Binds
    @ViewModelScope
    fun provideCatsRepository(catsRepository: CatsRepositoryImpl): CatsRepository

    @Binds
    @ViewModelScope
    fun provideFavoritesRepository(catsRepository: FavoritesRepositoryImpl): FavoritesRepository
}