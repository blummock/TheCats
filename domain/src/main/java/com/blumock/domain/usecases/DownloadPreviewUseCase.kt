package com.blumock.domain.usecases

import com.blumock.domain.services.DownloadPreviewService
import java.io.File
import javax.inject.Inject

class DownloadPreviewUseCase @Inject constructor(private val getImageService: DownloadPreviewService) : UseCase<String, File> {
    override suspend fun invoke(arg: String) = getImageService.get(arg)
}