package com.blumock.use_cases

import android.util.Log
import com.blumock.repository.CatsRepositoryImpl
import com.blumock.thecat.use_cases.CatsUseCase
import com.blumock.thecat.use_cases.FetchParams
import javax.inject.Inject

class CatsUseCaseImpl @Inject constructor(private val repository: CatsRepositoryImpl) : CatsUseCase {

    override suspend fun fetch(fetchParams: FetchParams) = try {
        val cats = repository.getCats(fetchParams)
        Result.success(cats)
    } catch (e: Exception) {
        Log.d(this.javaClass.simpleName, e.toString())
        Result.failure(e)
    }
}