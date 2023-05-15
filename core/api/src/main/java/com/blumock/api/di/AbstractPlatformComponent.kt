package com.blumock.api.di

import com.blumock.domain.services.ConnectionChecker
import com.blumock.domain.services.CreateFileService
import com.blumock.domain.services.DownloadImageService
import com.blumock.domain.services.DownloadPreviewService

interface AbstractPlatformComponent : AbstractAppComponent {

    fun provideConnectionChecker(): ConnectionChecker

    fun provideImageStore(): CreateFileService

    fun provideDownloadImageService(): DownloadImageService

    fun provideGetImageService(): DownloadPreviewService
}