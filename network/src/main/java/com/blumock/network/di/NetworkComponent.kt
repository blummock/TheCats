package com.blumock.network.di

import android.content.Context
import com.blumock.network.NetApi
import com.blumock.thecat.di.AbstractAppComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AbstractAppComponent::class],
    modules = [NetworkModule::class]
)
@Singleton
interface NetworkComponent {

    fun provideApi(): NetApi
    fun provideContext(): Context
}