package com.blumock.database.di

import com.blumock.database.dao.FavoritesDaoRoom
import com.blumock.thecat.di.AbstractAppComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AbstractAppComponent::class],
    modules = [DatabaseModule::class]
)
@Singleton
interface DatabaseComponent {

    fun provideDao(): FavoritesDaoRoom
}