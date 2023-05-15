package com.blumock.api.di

import android.content.Context

interface AbstractAppComponent {

    fun provideContext(): Context
    @BaseUrl
    fun provideBaseUrl(): String
}