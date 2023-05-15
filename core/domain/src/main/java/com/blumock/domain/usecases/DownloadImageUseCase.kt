package com.blumock.domain.usecases

import com.blumock.domain.services.DownloadImageService

class DownloadImageUseCase(private val downloadImageService: DownloadImageService) :
    UseCase<String, Unit> {

    override suspend fun invoke(arg: String) {
        downloadImageService.download(arg)
    }
}