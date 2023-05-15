package com.blumock.thecat.di

import com.blumock.api.di.BaseUrl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @BaseUrl
    @Singleton
    fun provideBaseUrl() = "https://api.thecatapi.com"
}