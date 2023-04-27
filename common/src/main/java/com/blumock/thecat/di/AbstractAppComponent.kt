package com.blumock.thecat.di

import android.content.Context

interface AbstractAppComponent {

    fun provideContext(): Context
}