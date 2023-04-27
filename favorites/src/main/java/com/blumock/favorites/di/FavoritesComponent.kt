package com.blumock.favorites.di

import com.blumock.favorites.ui.FavoritesFragment
import com.blumock.thecat.di.AbstractUseCasesComponent
import dagger.Component

@Component(
    dependencies = [AbstractUseCasesComponent::class],
    modules = [FavoritesViewModelModule::class]
)
interface FavoritesComponent {

    fun inject(fragment: FavoritesFragment)
}