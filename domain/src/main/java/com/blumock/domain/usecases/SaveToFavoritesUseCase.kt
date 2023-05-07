package com.blumock.domain.usecases

import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.domain.repositories.FavoritesRepository
import com.blumock.domain.services.CreateFileService
import java.io.File
import javax.inject.Inject

class SaveToFavoritesUseCase @Inject constructor(
    private val repository: FavoritesRepository,
    private val createFileService: CreateFileService
) :
    UseCase<@JvmSuppressWildcards Pair<String, File>, Unit> {
    override suspend fun invoke(arg: Pair<String, File>) {
        val file = createFileService.create()
        arg.second.copyTo(file)
        repository.save(
            FavoriteCatEntity(arg.first, file.path)
        )
    }
}