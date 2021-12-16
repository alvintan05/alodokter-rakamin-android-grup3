package com.grup3.alodokter_rakamin_android_grup3.adapters.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemLoadingStateBinding

class ArticleLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ArticleLoadStateAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder =
        ViewHolder(
            ItemLoadingStateBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            retry
        )

    inner class ViewHolder(
        private val binding: ItemLoadingStateBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.localizedMessage
            }
            binding.progressList.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState !is LoadState.Loading
            binding.tvError.isVisible = loadState !is LoadState.Loading
            binding.btnRetry.setOnClickListener {
                retry()
            }
        }
    }

}