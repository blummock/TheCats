package com.blumock.thecat.di

import com.blumock.api.di.AbstractActivityComponent
import com.blumock.api.di.AbstractRepositoryComponent
import com.blumock.api.di.ActivityScope
import dagger.Component

@Component(dependencies = [AbstractRepositoryComponent::class])
@ActivityScope
interface ActivityComponent : AbstractActivityComponent {

    companion object {
        fun create(repository: AbstractRepositoryComponent) = DaggerActivityComponent
            .builder()
            .abstractRepositoryComponent(repository)
            .build()
    }
}