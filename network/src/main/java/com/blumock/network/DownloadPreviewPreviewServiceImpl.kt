package com.blumock.network

import android.content.Context
import com.blumock.domain.services.DownloadPreviewService
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class DownloadPreviewPreviewServiceImpl @Inject constructor(private val context: Context) : DownloadPreviewService {
    override suspend fun get(url: String): File = withContext(Dispatchers.IO) {
        Glide.with(context)
            .asFile()
            .encodeQuality(5)
            .load(url)
            .submit()
            .get()
    }
}