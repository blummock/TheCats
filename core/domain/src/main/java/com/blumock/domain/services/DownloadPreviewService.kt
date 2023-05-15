package com.blumock.domain.services

import java.io.File

interface DownloadPreviewService {

    suspend fun get(url: String): File
}