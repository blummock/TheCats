package com.blumock.domain.services

interface DownloadImageService {

    suspend fun download(url: String)
}