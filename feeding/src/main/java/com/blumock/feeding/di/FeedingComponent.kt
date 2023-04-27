package com.blumock.feeding.di

import com.blumock.feeding.ui.FeedingFragment
import com.blumock.thecat.di.AbstractUseCasesComponent
import dagger.Component

@Component(
    dependencies = [AbstractUseCasesComponent::class],
    modules = [FeedingViewModelModule::class]
)
interface FeedingComponent {

    fun inject(fragment: FeedingFragment)
}