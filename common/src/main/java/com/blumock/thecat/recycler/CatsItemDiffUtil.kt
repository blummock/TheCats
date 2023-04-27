package com.blumock.thecat.recycler

import androidx.recyclerview.widget.DiffUtil
import com.blumock.thecat.data.CatsItem

class CatsItemDiffUtil : DiffUtil.ItemCallback<CatsItem>() {

    override fun areItemsTheSame(oldItem: CatsItem, newItem: CatsItem) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: CatsItem, newItem: CatsItem) = oldItem == newItem
}