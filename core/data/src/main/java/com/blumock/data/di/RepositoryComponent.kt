package com.blumock.data.di

import com.blumock.api.di.AbstractPlatformComponent
import com.blumock.api.di.AbstractRepositoryComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AbstractPlatformComponent::class],
    modules = [NetworkModule::class, DatabaseModule::class, RepositoryModule::class]
)
@Singleton
interface RepositoryComponent : AbstractRepositoryComponent {

    companion object {
        fun create(platformComponent: AbstractPlatformComponent) = DaggerRepositoryComponent
            .builder()
            .abstractPlatformComponent(platformComponent)
            .build()
    }
}