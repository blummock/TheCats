package com.blumock.thecat

import android.app.Application
import com.blumock.database.di.DaggerDatabaseComponent
import com.blumock.database.di.DatabaseComponent
import com.blumock.network.di.DaggerNetworkComponent
import com.blumock.network.di.NetworkComponent
import com.blumock.thecat.di.DaggerAppComponent

class App : Application() {

    lateinit var networkComponent: NetworkComponent
        private set

    lateinit var databaseComponent: DatabaseComponent
        private set

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()
        networkComponent = DaggerNetworkComponent.builder()
            .abstractAppComponent(appComponent)
            .build()
        databaseComponent = DaggerDatabaseComponent.builder()
            .abstractAppComponent(appComponent)
            .build()
    }
}