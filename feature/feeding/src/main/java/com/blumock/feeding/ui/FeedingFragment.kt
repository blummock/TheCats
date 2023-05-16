package com.blumock.feeding.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blumock.api.activity.AbstractActivity
import com.blumock.common.databinding.FragmentRecyclerBinding
import com.blumock.common.recycler.Decorator
import com.blumock.common.view_model.ViewModelFactory
import com.blumock.feeding.di.FeedingComponent
import com.blumock.feeding.recycler.DownloadDialog
import com.blumock.feeding.recycler.FeedRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var binding: FragmentRecyclerBinding

    private val viewModel by viewModels<FeedingViewModel> {
        viewModelFactory
    }

    private lateinit var adapter: FeedRecyclerAdapter

    companion object {
        fun newInstance() = FeedingFragment()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        FeedingComponent.create((requireActivity() as AbstractActivity).getComponent()).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FeedRecyclerAdapter(lifecycle, viewModel, DownloadDialog(requireContext()))
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loading
                        .collect {
                            (requireActivity() as AbstractActivity).progressIndicator(it)
                        }
                }
                launch {
                    viewModel.cats
                        .collect {
                            adapter.submitData(it)
                        }
                }
            }
        }
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
            adapter = this@FeedingFragment.adapter
            addItemDecoration(Decorator(2))
        }

        lifecycleScope.launch {
            viewModel.messages.collect {
                when (it) {
                    is FeedingViewModel.Message.Empty -> return@collect
                    is FeedingViewModel.Message.Res -> Snackbar.make(view, it.res, Snackbar.LENGTH_SHORT).show()
                    is FeedingViewModel.Message.Text -> Snackbar.make(view, it.text, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
}