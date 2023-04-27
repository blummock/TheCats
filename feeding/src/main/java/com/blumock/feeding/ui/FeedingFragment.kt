package com.blumock.feeding.ui

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blumock.feeding.R
import com.blumock.feeding.databinding.ItemCardBinding
import com.blumock.feeding.di.DaggerFeedingComponent
import com.blumock.thecat.activity.AbstractActivity
import com.blumock.thecat.data.CatsItem
import com.blumock.thecat.recycler.CatsItemDiffUtil
import com.blumock.thecat.recycler.Decorator
import com.blumock.thecat.view_model.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FeedingFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<FeedingViewModel> {
        viewModelFactory
    }

    private lateinit var catsFeedAdapter: CatsFeedAdapter

    companion object {
        fun newInstance() = FeedingFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFeedingComponent.builder()
            .abstractUseCasesComponent((requireActivity() as AbstractActivity).getComponent())
            .build()
            .inject(this)
        catsFeedAdapter = CatsFeedAdapter()
        lifecycleScope.launch {
            viewModel.cats
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    catsFeedAdapter.submitData(it)
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
            adapter = catsFeedAdapter
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

    private inner class CatsItemHolder(private val binding: ItemCardBinding, private val notify: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        private var position: Int? = null
        private var item: CatsItem? = null
        private val scope = CoroutineScope(CoroutineName("Holders") + SupervisorJob())


        init {
            binding.like.setOnClickListener { _ ->
                item?.let {
                    if (!it.isFav) {
                        it.isFav = true
                        viewModel.saveToFavorites(catsItem = it)
                        notify(position!!)
                    }
                }
            }
            binding.root.setOnClickListener {
                item?.url?.let {
                    lifecycleScope.launch {
                        dialog {
                            downloadImage(it)
                        }
                    }
                }
            }
        }

        fun bind(item: CatsItem, position: Int, payloads: MutableList<Any>?) {
            this.position = position
            this.item = item
            binding.like.isSelected = item.isFav
            payloads?.let {
                if (it.isEmpty()) return@let
                binding.like.isSelected = it.last() as Boolean
                return@bind
            }
            binding.image.setImageResource(com.blumock.common.R.color.gray)
            scope.coroutineContext.job.cancelChildren()
            scope.launch(Dispatchers.Default) {
                while (item.image == null) {
                    delay(100)
                }
                withContext(Dispatchers.Main) {
                    binding.image.setImageBitmap(item.image)
                }
            }
        }
    }

    private inner class CatsFeedAdapter :
        PagingDataAdapter<CatsItem, CatsItemHolder>(CatsItemDiffUtil()) {

        override fun onBindViewHolder(holder: CatsItemHolder, position: Int, payloads: MutableList<Any>) {
            getItem(position)?.let { holder.bind(it, position, payloads) }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsItemHolder {
            return CatsItemHolder(
                ItemCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            ) {
                notifyItemChanged(it, true)
            }
        }

        override fun onBindViewHolder(holder: CatsItemHolder, position: Int) {
            getItem(position)?.let { holder.bind(it, position, null) }
        }
    }

    private fun downloadImage(url: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        val fileName = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".jpg"
        request.setTitle(getString(R.string.download_title, fileName))
        request.setDescription(getString(R.string.download_notification))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName)

        val downloadManager = requireContext().getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
        Toast.makeText(requireContext(), getString(R.string.download_notification), Toast.LENGTH_SHORT).show()
    }

    private fun dialog(action: () -> Unit) {
        MaterialAlertDialogBuilder(requireContext()).apply {
            setTitle(getString(R.string.download_notification))
            setMessage(getString(R.string.download_dialog_message))
            setPositiveButton(getString(R.string.yes)) { a, _ ->
                action()
                a.cancel()
            }
            setNegativeButton(getString(R.string.no)) { a, _ -> a.cancel() }
            show()
        }
    }
}