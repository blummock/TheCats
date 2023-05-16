package com.blumock.feeding.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.blumock.feeding.databinding.ItemCardBinding
import com.blumock.feeding.ui.FeedingViewModel

class FeedRecyclerAdapter(
    private val lifecycle: Lifecycle,
    private val feedingViewModel: FeedingViewModel,
    private val downloadDialog: DownloadDialog,
) : PagingDataAdapter<CatItemModel, ItemViewHolder>(ItemDiffUtil()) {

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        getItem(position)?.let { holder.bind(it, payloads) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            lifecycle,
            feedingViewModel,
            downloadDialog
        ) { position, flag ->
            notifyItemChanged(position, flag)
        }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, null) }
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