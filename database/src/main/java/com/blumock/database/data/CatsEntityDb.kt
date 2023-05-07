package com.blumock.database.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_cats")
data class CatsEntityDb(
    @PrimaryKey @ColumnInfo(name = "id") var id: String,
    @ColumnInfo(name = "image_uri") var image: String
)
