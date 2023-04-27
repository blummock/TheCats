package com.blumock.thecat.image

import com.blumock.thecat.data.CatsItem

interface ImageLoader {

    fun load(catsItem: List<CatsItem>)
}