package com.blumock.thecat.image

import android.graphics.Bitmap

interface ImageStore {

    fun putImg(image: Bitmap): String
}