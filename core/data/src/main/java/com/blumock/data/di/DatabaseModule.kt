package com.blumock.data.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.blumock.data.db.Database
import com.blumock.data.db.dao.FavoritesDaoRoom
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton


@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room.databaseBuilder(context, Database::class.java, "cats-database")
            .setQueryCallback(
                { sqlQuery, bindArgs -> Log.d("Room", "SQL Query: $sqlQuery SQL Args: $bindArgs") },
                Executors.newSingleThreadExecutor()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(database: Database): FavoritesDaoRoom =
        database.favoritesDao()
}