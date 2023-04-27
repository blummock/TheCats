package com.blumock.favorites.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.use_cases.FavoritesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    favoritesUseCase: FavoritesUseCase
) : ViewModel() {

    private val _favorites = MutableLiveData<List<CatsItem>>()
    val favorites: LiveData<List<CatsItem>> = _favorites

    private val _errors = MutableLiveData<String>()
    val errors: LiveData<String> = _errors

    init {
        viewModelScope.launch {
            favoritesUseCase.fetch().onSuccess {
                _favorites.postValue(it)
            }.onFailure {
                _errors.postValue(it.message)
            }
        }
    }

}