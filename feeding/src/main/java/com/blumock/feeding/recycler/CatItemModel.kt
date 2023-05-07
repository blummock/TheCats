package com.blumock.feeding.recycler

import com.blumock.domain.usecases.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CatItemModel(
    val id: String,
    val url: String,
    var isFavorite: Boolean = false,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getImageUseCase: UseCase<String, File>
) {
    suspend fun getImage() = withContext(dispatcher) {
        getImageUseCase(url)
    }
}