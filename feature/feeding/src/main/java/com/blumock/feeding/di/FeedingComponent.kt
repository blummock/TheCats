package com.blumock.feeding.di

import com.blumock.api.di.AbstractUseCasesComponent
import com.blumock.api.di.FragmentScope
import com.blumock.feeding.ui.FeedingFragment
import dagger.Component

@Component(
    dependencies = [AbstractUseCasesComponent::class],
    modules = [FeedingViewModelModule::class]
)
@FragmentScope
interface FeedingComponent {

    companion object {
        fun create(useCasesComponent: AbstractUseCasesComponent) = DaggerFeedingComponent
            .builder()
            .abstractUseCasesComponent(useCasesComponent)
            .build()
    }

    fun inject(fragment: FeedingFragment)
}