package com.blumock.domain.usecases

import com.blumock.domain.services.DownloadImageService
import javax.inject.Inject

class DownloadImageUseCase @Inject constructor(private val downloadImageService: DownloadImageService) :
    UseCase<String, Unit> {

    override suspend fun invoke(arg: String) {
        downloadImageService.download(arg)
    }
}