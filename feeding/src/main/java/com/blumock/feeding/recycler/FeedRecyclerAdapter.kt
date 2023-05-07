package com.blumock.feeding.recycler

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.blumock.feeding.R
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

class FeedRecyclerAdapter(
    private val lifecycle: Lifecycle,
    private val feedingViewModel: FeedingViewModel,
    private val downloadDialog: DownloadDialog,
) : PagingDataAdapter<CatItemModel, FeedRecyclerAdapter.RecyclerItemHolder>(ItemDiffUtil()) {

    override fun onBindViewHolder(
        holder: FeedRecyclerAdapter.RecyclerItemHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position)?.let { holder.bind(it, payloads) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RecyclerItemHolder(ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun onBindViewHolder(holder: FeedRecyclerAdapter.RecyclerItemHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, null) }
    }

    inner class RecyclerItemHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
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
                            Log.e(this@RecyclerItemHolder.javaClass.simpleName, "Glade load image", e)
                        }
                    }
            }

            binding.like.setOnClickListener { _ ->
                holder.get()?.let {
                    if (!it.first.isFavorite) {
                        feedingViewModel.saveToFavorites(it.first.id to it.second)
                        it.first.isFavorite = true
                        snapshot().get(0)
                        notifyItemChanged(absoluteAdapterPosition, true)
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
            binding.image.setImageResource(R.drawable.ic_baseline_wallpaper_24)
            binding.like.isSelected = item.isFavorite
            holder.set(null)
            this.item.tryEmit(item)
        }
    }
}

private class ItemDiffUtil() : DiffUtil.ItemCallback<CatItemModel>() {

    override fun areItemsTheSame(oldItem: CatItemModel, newItem: CatItemModel): Boolean {
        val res = oldItem.id == newItem.id
        return res
    }

    override fun areContentsTheSame(oldItem: CatItemModel, newItem: CatItemModel): Boolean {
        val res = oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite
        return res
    }
}