package com.blumock.favorites.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.blumock.domain.models.FavoriteCatEntity
import com.blumock.favorites.databinding.FavoritesItemCardBinding
import com.blumock.favorites.di.DaggerFavoritesComponent
import com.blumock.thecat.activity.AbstractActivity
import com.blumock.thecat.recycler.Decorator
import com.blumock.thecat.view_model.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<FavoritesViewModel> {
        viewModelFactory
    }

    private lateinit var favoritesAdapter: FavoritesAdapter

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoritesComponent.builder()
            .abstractUseCasesComponent((requireActivity() as AbstractActivity).getComponent())
            .build()
            .inject(this)
        favoritesAdapter = FavoritesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(com.blumock.common.R.layout.fragment_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(com.blumock.common.R.id.recycler).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
            addItemDecoration(Decorator(2))
        }
        viewModel.favorites.observe(viewLifecycleOwner) {
            favoritesAdapter.submitList(it)
        }
        viewModel.errors.observe(viewLifecycleOwner) {
            Snackbar.make(
                view,
                it ?: requireContext().getString(com.blumock.common.R.string.unknown_error),
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private class CatsItemHolder(private val binding: FavoritesItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteCatEntity) {
            binding.image.setImageDrawable(Drawable.createFromPath(item.image))
//            item.image?.let { binding.image.setImageBitmap(it) }
        }
    }

    private class FavoritesAdapter : ListAdapter<FavoriteCatEntity, CatsItemHolder>(ItemDiffUtil()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsItemHolder {
            return CatsItemHolder(
                FavoritesItemCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: CatsItemHolder, position: Int) {
            getItem(position)?.let { holder.bind(it) }
        }
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<FavoriteCatEntity>() {

        override fun areItemsTheSame(oldItem: FavoriteCatEntity, newItem: FavoriteCatEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteCatEntity, newItem: FavoriteCatEntity): Boolean {
            return oldItem == newItem
        }
    }
}