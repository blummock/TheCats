package com.blumock.thecat.di.scope

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModelScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope