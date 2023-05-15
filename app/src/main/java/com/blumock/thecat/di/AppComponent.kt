package com.blumock.thecat.di

import android.content.Context
import com.blumock.api.di.AbstractAppComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent : AbstractAppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(context: Context): Builder
        fun build(): AppComponent
    }

    companion object {
        fun create(context: Context) = DaggerAppComponent
            .builder()
            .application(context)
            .build()
    }
}
