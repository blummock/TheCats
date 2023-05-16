package com.blumock.favorites.ui

import android.content.Context
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
import com.blumock.api.activity.AbstractActivity
import com.blumock.common.databinding.FragmentRecyclerBinding
import com.blumock.common.view_model.ViewModelFactory
import com.blumock.domain.models.FavoriteCatModel
import com.blumock.favorites.databinding.FavoritesItemCardBinding
import com.blumock.favorites.di.FavoritesComponent
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var binding: FragmentRecyclerBinding

    private val viewModel by viewModels<FavoritesViewModel> {
        viewModelFactory
    }

    private lateinit var favoritesAdapter: FavoritesAdapter

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        FavoritesComponent.create((requireActivity() as AbstractActivity).getComponent()).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoritesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRecyclerBinding.inflate(inflater, container, false).also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(com.blumock.common.R.id.recycler).apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
            addItemDecoration(com.blumock.common.recycler.Decorator(2))
        }
        viewModel.favorites.observe(viewLifecycleOwner) {
            favoritesAdapter.submitList(it)
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            (requireActivity() as AbstractActivity).progressIndicator(false)
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
        fun bind(item: FavoriteCatModel) {
            binding.image.setImageDrawable(Drawable.createFromPath(item.image))
        }
    }

    private class FavoritesAdapter : ListAdapter<FavoriteCatModel, CatsItemHolder>(ItemDiffUtil()) {

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

    class ItemDiffUtil : DiffUtil.ItemCallback<FavoriteCatModel>() {

        override fun areItemsTheSame(oldItem: FavoriteCatModel, newItem: FavoriteCatModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteCatModel, newItem: FavoriteCatModel): Boolean {
            return oldItem == newItem
        }
    }
}