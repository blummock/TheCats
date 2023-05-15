package com.blumock.data.di

import com.blumock.api.di.AbstractRepositoryComponent
import com.blumock.api.di.AbstractUseCasesComponent
import com.blumock.api.di.FragmentScope
import dagger.Component

@Component(
    dependencies = [AbstractRepositoryComponent::class],
    modules = [UseCasesModule::class]
)
@FragmentScope
interface UseCaseComponent : AbstractUseCasesComponent {

    companion object {
        fun create(repositoryComponent: AbstractRepositoryComponent) = DaggerUseCaseComponent
            .builder()
            .abstractRepositoryComponent(repositoryComponent)
            .build()
    }
}