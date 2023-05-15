package com.blumock.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blumock.data.db.dao.FavoritesDaoRoom
import com.blumock.data.db.model.CatsEntityDb

@Database(entities = [CatsEntityDb::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDaoRoom
}