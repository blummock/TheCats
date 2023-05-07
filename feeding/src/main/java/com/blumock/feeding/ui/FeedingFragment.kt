package com.blumock.feeding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blumock.feeding.di.DaggerFeedingComponent
import com.blumock.feeding.recycler.DownloadDialog
import com.blumock.feeding.recycler.FeedRecyclerAdapter
import com.blumock.thecat.activity.AbstractActivity
import com.blumock.thecat.recycler.Decorator
import com.blumock.thecat.view_model.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<FeedingViewModel> {
        viewModelFactory
    }

    private lateinit var adapter: FeedRecyclerAdapter

    companion object {
        fun newInstance() = FeedingFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFeedingComponent.builder()
            .abstractUseCasesComponent((requireActivity() as AbstractActivity).getComponent())
            .build()
            .inject(this)
        adapter = FeedRecyclerAdapter(lifecycle, viewModel, DownloadDialog(requireContext()))
        lifecycleScope.launch {
            viewModel.cats
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    adapter.submitData(it)
                }
        }
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