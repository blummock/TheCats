package com.blumock.platform

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import com.blumock.domain.services.DownloadImageService
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DownloadImageServiceImpl @Inject constructor(private val context: Context) : DownloadImageService {
    override suspend fun download(url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        val fileName = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"
        request.setTitle(context.getString(com.blumock.common.R.string.download_title, fileName))
        request.setDescription(context.getString(com.blumock.common.R.string.download_notification))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(
            context,
            context.getString(com.blumock.common.R.string.download_notification),
            Toast.LENGTH_SHORT
        ).show()
    }
}