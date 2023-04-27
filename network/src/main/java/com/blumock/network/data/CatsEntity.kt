package com.blumock.network.data

import com.blumock.thecat.data.CatsItem

data class CatsEntity(val id: String, val url: String, val with: Int, val height: Int)

fun CatsEntity.toCatsItem(): CatsItem {
    return CatsItem(
        id = this.id,
        url = this.url,
        with = this.with,
        height = this.height
    )
}