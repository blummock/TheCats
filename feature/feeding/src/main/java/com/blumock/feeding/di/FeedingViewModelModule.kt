package com.blumock.feeding.di

import com.blumock.api.di.FragmentScope
import com.blumock.feeding.ui.FeedingViewModel
import com.blumock.ui.view_model.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class FeedingViewModelModule {

    @Provides
    @FragmentScope
    fun provideViewFactory(provider: Provider<FeedingViewModel>): ViewModelFactory =
        ViewModelFactory(provider)
}