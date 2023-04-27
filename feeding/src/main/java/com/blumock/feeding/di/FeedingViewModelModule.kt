package com.blumock.feeding.di

import com.blumock.feeding.ui.FeedingViewModel
import com.blumock.thecat.view_model.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class FeedingViewModelModule {

    @Provides
    fun provideViewFactory(provider: Provider<FeedingViewModel>): ViewModelFactory = ViewModelFactory(provider)
}