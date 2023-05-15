package com.blumock.data.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.blumock.data.db.model.CatsEntityDb

@Dao
interface FavoritesDaoRoom {

    @Query("SELECT * FROM fav_cats")
    suspend fun selectCats(): List<CatsEntityDb>

    @Insert
    suspend fun insert(item: CatsEntityDb)
}