package com.blumock.thecat.data

import android.graphics.Bitmap

data class CatsItem(
    val id: String,
    val url: String,
    val with: Int,
    val height: Int,
    var isFav: Boolean = false,
    var image: Bitmap? = null
)
