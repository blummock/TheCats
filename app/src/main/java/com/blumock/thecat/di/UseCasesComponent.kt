package com.blumock.thecat.di

import com.blumock.di.RepositoryModule
import com.blumock.di.UseCasesModule
import com.blumock.thecat.di.scope.ViewModelScope
import dagger.Component

@Component(
    dependencies = [ActivityComponent::class],
    modules = [UseCasesModule::class, RepositoryModule::class]
)
@ViewModelScope
interface UseCasesComponent : AbstractUseCasesComponent {

}