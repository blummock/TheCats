package com.blumock.feeding.recycler

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.blumock.feeding.databinding.ItemCardBinding
import com.blumock.feeding.ui.FeedingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.util.concurrent.atomic.AtomicReference

class ItemViewHolder(
    private val binding: ItemCardBinding,
    private val lifecycle: Lifecycle,
    private val feedingViewModel: FeedingViewModel,
    private val downloadDialog: DownloadDialog,
    private val notify: (Int, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private val holder = AtomicReference<Pair<CatItemModel, File>?>()
    private val item =
        MutableSharedFlow<CatItemModel>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    init {
        lifecycle.coroutineScope.launch {
            item
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { item ->
                    try {
                        val file = item.getImage()
                        val bitmap = withContext(Dispatchers.IO) {
                            BitmapFactory.decodeStream(FileInputStream(file))
                        }
                        withContext(Dispatchers.Main) {
                            binding.image.setImageBitmap(bitmap)
                        }
                        holder.set(item to file)
                    } catch (e: Exception) {
                        Log.e(this.javaClass.simpleName, "Glade load image", e)
                    }
                }
        }

        binding.like.setOnClickListener { _ ->
            holder.get()?.let {
                if (!it.first.isFavorite) {
                    feedingViewModel.saveToFavorites(it.first.id to it.second)
                    it.first.isFavorite = true
                    notify(absoluteAdapterPosition, true)
                }
            }
        }

        binding.root.setOnClickListener {
            lifecycle.coroutineScope.launch {
                holder.get()?.let {
                    if (downloadDialog()) {
                        feedingViewModel.download(it.first.url)
                    }
                }
            }
        }
    }

    fun bind(item: CatItemModel, payloads: MutableList<Any>?) {
        payloads?.let {
            if (it.isEmpty()) return@let
            binding.like.isSelected = it.last() as Boolean
            return@bind
        }
        binding.image.setImageResource(com.blumock.common.R.drawable.ic_baseline_wallpaper_24)
        binding.like.isSelected = item.isFavorite
        holder.set(null)
        this.item.tryEmit(item)
    }
}