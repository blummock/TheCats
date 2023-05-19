package com.blumock.favorites.di

import com.blumock.api.di.FragmentScope
import com.blumock.favorites.ui.FavoritesViewModel
import com.blumock.ui.view_model.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Provider

@Module
class FavoritesViewModelModule {

    @Provides
    @FragmentScope
    fun provideViewFactory(provider: Provider<FavoritesViewModel>): ViewModelFactory =
        ViewModelFactory(provider)
}