package com.blumock.network

import android.content.Context
import com.blumock.domain.services.CreateFileService
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val IMAGES = "images_fav"

class CreateFileServiceImpl @Inject constructor(private val context: Context) : CreateFileService {

    override fun create(): File {
        val fileName = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val images = context.getDir(IMAGES, Context.MODE_PRIVATE)
        if (!images.exists()) {
            images.mkdir()
        }
        return File(images, fileName)
    }

}