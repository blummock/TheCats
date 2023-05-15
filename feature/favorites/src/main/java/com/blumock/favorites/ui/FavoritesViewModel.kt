package com.blumock.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumock.api.di.FragmentScope
import com.blumock.domain.models.FavoriteCatModel
import com.blumock.domain.usecases.UseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class FavoritesViewModel @Inject constructor(
    getFavoritesUseCase: UseCase<Unit, Result<List<FavoriteCatModel>>>
) : ViewModel() {

    private val _favorites = MutableLiveData<List<FavoriteCatModel>>()
    val favorites: LiveData<List<FavoriteCatModel>> = _favorites

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