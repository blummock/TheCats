package com.blumock.api.activity

import com.blumock.api.di.AbstractUseCasesComponent

interface AbstractActivity {

    fun getComponent(): AbstractUseCasesComponent
}