package com.blumock.platform.di

import com.blumock.api.di.AbstractAppComponent
import com.blumock.api.di.AbstractPlatformComponent
import dagger.Component
import javax.inject.Singleton

@Component(
    dependencies = [AbstractAppComponent::class],
    modules = [PlatformModule::class]
)
@Singleton
interface PlatformComponent : AbstractPlatformComponent {

    companion object {
        fun create(appComponent: AbstractAppComponent) = DaggerPlatformComponent
            .builder()
            .abstractAppComponent(appComponent)
            .build()
    }
}