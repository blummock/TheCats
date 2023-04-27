package com.blumock.network.images

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.image.ImageLoader
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageLoaderImpl @Inject constructor(
    private val context: Context
) : ImageLoader {

    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob())
    private val empty by lazy { BitmapFactory.decodeResource(context.resources, com.blumock.common.R.raw.empty_img) }

    override fun load(catsItem: List<CatsItem>) {
        catsItem.forEach {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    it.image = Glide.with(context)
                        .asBitmap()
                        .encodeQuality(5)
                        .load(it.url)
                        .submit()
                        .get()

                } catch (e: Exception) {
                    Log.e(this.javaClass.name, "load image")
                    it.image = empty
                }
            }
        }
    }
}