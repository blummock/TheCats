package com.blumock.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blumock.database.dao.FavoritesDaoRoom
import com.blumock.database.data.CatsEntityDb

@Database(entities = [CatsEntityDb::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDaoRoom
}