package com.blumock.platform.di

import com.blumock.domain.services.ConnectionChecker
import com.blumock.domain.services.CreateFileService
import com.blumock.domain.services.DownloadImageService
import com.blumock.domain.services.DownloadPreviewService
import com.blumock.platform.ConnectionCheckerImpl
import com.blumock.platform.CreateFileServiceImpl
import com.blumock.platform.DownloadImageServiceImpl
import com.blumock.platform.DownloadPreviewServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface PlatformModule {

    @Binds
    @Singleton
    fun provideConnectionChecker(connectionChecker: ConnectionCheckerImpl): ConnectionChecker

    @Binds
    @Singleton
    fun provideImageStore(createFileService: CreateFileServiceImpl): CreateFileService

    @Binds
    @Singleton
    fun provideDownloadImageService(downloadImageService: DownloadImageServiceImpl): DownloadImageService

    @Binds
    @Singleton
    fun provideGetImageService(getImageService: DownloadPreviewServiceImpl): DownloadPreviewService

}