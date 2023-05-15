package com.blumock.thecat

import android.app.Application
import com.blumock.api.di.AbstractRepositoryComponent
import com.blumock.data.di.RepositoryComponent
import com.blumock.platform.di.PlatformComponent
import com.blumock.thecat.di.AppComponent

class App : Application() {

    lateinit var component: AbstractRepositoryComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = RepositoryComponent.create(PlatformComponent.create(AppComponent.create(this)))
    }
}