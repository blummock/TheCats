package com.blumock.domain.usecases

import com.blumock.domain.models.FavoriteCatModel
import com.blumock.domain.repositories.FavoritesRepository
import com.blumock.domain.services.CreateFileService
import java.io.File

class SaveToFavoritesUseCase(
    private val repository: FavoritesRepository,
    private val createFileService: CreateFileService
) :
    UseCase<@JvmSuppressWildcards Pair<String, File>, Unit> {
    override suspend fun invoke(arg: Pair<String, File>) {
        val file = createFileService.create()
        arg.second.copyTo(file)
        repository.save(
            FavoriteCatModel(arg.first, file.path)
        )
    }
}