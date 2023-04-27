package com.blumock.feeding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.blumock.feeding.R
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.use_cases.CatsUseCase
import com.blumock.thecat.use_cases.FetchParams
import com.blumock.thecat.use_cases.SaveFavoritesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedingViewModel @Inject constructor(
    private val useCases: CatsUseCase,
    private val saveFavoritesUseCase: SaveFavoritesUseCase
) : ViewModel() {

    private val _messages = MutableSharedFlow<Message>()
    val messages = _messages.asSharedFlow()


    val cats = Pager(
        config = PagingConfig(
            pageSize = 10,
        ),
        pagingSourceFactory = {
            CatsPaging(useCases = useCases, _messages)
        }
    ).flow
        .cachedIn(viewModelScope)

    fun saveToFavorites(catsItem: CatsItem) {
        viewModelScope.launch {
            saveFavoritesUseCase.save(catsItem)
            _messages.emit(Message.Res(R.string.in_favorites))
        }
    }

    private class CatsPaging(private val useCases: CatsUseCase, private val errors: MutableSharedFlow<Message>) :
        PagingSource<Int, CatsItem>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatsItem> {
            val nextPageNumber = params.key ?: 0

            return useCases.fetch(FetchParams(limit = 10, page = nextPageNumber)).fold({
                LoadResult.Page(
                    data = it,
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber + 1
                )
            }, { e ->
                errors.emit(e.message?.let { Message.Text(it) }
                    ?: Message.Res(com.blumock.common.R.string.unknown_error))
                LoadResult.Error(e)
            })
        }

        override fun getRefreshKey(state: PagingState<Int, CatsItem>): Int {
            return 0
        }
    }

    sealed interface Message {
        object Empty : Message
        data class Res(val res: Int) : Message
        data class Text(val text: String) : Message
    }
}