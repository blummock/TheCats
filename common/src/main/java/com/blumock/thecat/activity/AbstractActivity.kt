package com.blumock.thecat.activity

import com.blumock.thecat.di.AbstractUseCasesComponent

interface AbstractActivity {

    fun getComponent(): AbstractUseCasesComponent
}