package com.blumock.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_cats")
data class CatsEntityDb(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "with") var with: Int,
    @ColumnInfo(name = "height") var height: Int,
    @ColumnInfo(name = "image_uri") var image: String
)
