package com.blumock.feeding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.blumock.api.di.FragmentScope
import com.blumock.domain.models.CatModel
import com.blumock.domain.models.GetCatsArgs
import com.blumock.domain.usecases.UseCase
import com.blumock.feeding.recycler.CatItemModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@FragmentScope
class FeedingViewModel @Inject constructor(
    private val getImageUseCase: UseCase<String, File>,
    private val downloadImageUseCase: UseCase<String, Unit>,
    private val saveToFavoritesUseCase: UseCase<Pair<String, File>, Unit>,
    private val getCatsUseCase: UseCase<GetCatsArgs, Result<List<CatModel>>>
) : ViewModel() {

    private val _messages = MutableSharedFlow<Message>()
    val messages = _messages.asSharedFlow()

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> = _loading

    val cats = Pager(
        config = PagingConfig(
            pageSize = 3,
        ),
        pagingSourceFactory = {
            CatsPaging(getCatsUseCase = getCatsUseCase, _messages, loading)
        }
    ).flow
        .map {
            it.map { item ->
                CatItemModel(
                    id = item.id,
                    url = item.url,
                    isFavorite = item.isFavorite,
                    getImageUseCase = getImageUseCase
                )
            }
        }
        .cachedIn(viewModelScope)

    fun saveToFavorites(pair: Pair<String, File>) {
        viewModelScope.launch {
            saveToFavoritesUseCase(pair)
            _messages.emit(Message.Res(com.blumock.common.R.string.in_favorites))
        }
    }

    fun download(url: String) {
        viewModelScope.launch {
            downloadImageUseCase(url)
        }
    }

    private class CatsPaging(
        private val getCatsUseCase: UseCase<GetCatsArgs, Result<List<CatModel>>>,
        private val errors: MutableSharedFlow<Message>,
        private val loading: MutableSharedFlow<Boolean>
    ) :
        PagingSource<Int, CatModel>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatModel> {
            val nextPageNumber = params.key ?: 0
            val itemsSize = params.loadSize + 1
            loading.emit(true)
            return getCatsUseCase(GetCatsArgs(limit = itemsSize, page = nextPageNumber)).fold({
                loading.emit(false)
                LoadResult.Page(
                    data = it,
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber + 1,
                    itemsBefore = nextPageNumber * nextPageNumber,
                    itemsAfter = itemsSize
                )
            }, { e ->
                loading.emit(false)
                errors.emit(e.message?.let { Message.Text(it) }
                    ?: Message.Res(com.blumock.common.R.string.unknown_error))
                LoadResult.Error(e)
            })
        }

        override fun getRefreshKey(state: PagingState<Int, CatModel>): Int {
            return 0
        }
    }

    sealed interface Message {
        object Empty : Message
        data class Res(val res: Int) : Message
        data class Text(val text: String) : Message
    }
}