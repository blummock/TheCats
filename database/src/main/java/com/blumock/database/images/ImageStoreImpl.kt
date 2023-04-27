package com.blumock.database.images

import android.content.Context
import android.graphics.Bitmap
import com.blumock.thecat.image.ImageStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val IMAGES = "images_fav"

class ImageStoreImpl @Inject constructor(private val context: Context) : ImageStore {

    override fun putImg(image: Bitmap): String {
        val fileName = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val images = context.getDir(IMAGES, Context.MODE_PRIVATE)
        if (!images.exists()) {
            images.mkdir()
        }
        val cached = File(images, fileName)
        cached.outputStream().use {
            image.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return cached.path
    }
}