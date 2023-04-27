package com.blumock.thecat.di

import android.content.Context
import com.blumock.database.dao.FavoritesDaoRoom
import com.blumock.database.di.DatabaseComponent
import com.blumock.network.NetApi
import com.blumock.network.di.NetworkComponent
import com.blumock.thecat.di.scope.ActivityScope
import dagger.Component

@Component(dependencies = [NetworkComponent::class, DatabaseComponent::class])
@ActivityScope
interface ActivityComponent {

    fun provideApi(): NetApi
    fun provideContext(): Context
    fun provideDao(): FavoritesDaoRoom
}