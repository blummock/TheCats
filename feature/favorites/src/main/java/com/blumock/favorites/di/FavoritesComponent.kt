package com.blumock.favorites.di

import com.blumock.api.di.AbstractUseCasesComponent
import com.blumock.api.di.FragmentScope
import com.blumock.favorites.ui.FavoritesFragment
import dagger.Component

@Component(
    dependencies = [AbstractUseCasesComponent::class],
    modules = [FavoritesViewModelModule::class]
)
@FragmentScope
interface FavoritesComponent {

    companion object {
        fun create(useCasesComponent: AbstractUseCasesComponent) = DaggerFavoritesComponent
            .builder()
            .abstractUseCasesComponent(useCasesComponent)
            .build()
    }

    fun inject(fragment: FavoritesFragment)
}