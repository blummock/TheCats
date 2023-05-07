package com.blumock.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.domain.usecases.UseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    getFavoritesUseCase: UseCase<Unit, Result<List<FavoriteCatEntity>>>
) : ViewModel() {

    private val _favorites = MutableLiveData<List<FavoriteCatEntity>>()
    val favorites: LiveData<List<FavoriteCatEntity>> = _favorites

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    init {
        viewModelScope.launch {
            getFavoritesUseCase(Unit)
                .onSuccess { _favorites.postValue(it) }
                .onFailure { _errors.postValue(it.message) }
        }
    }
}