package com.grup3.alodokter_rakamin_android_grup3.adapters.pagination

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity

class ArticlePagingAdapter : PagingDataAdapter<ArticleEntity, ArticlePagingAdapter.ViewHolder>(
    DIFF_CALLBACK
) {

    var onClickListener: ((item: Any?) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticleEntity>() {
            override fun areItemsTheSame(
                oldEntity: ArticleEntity,
                newEntity: ArticleEntity
            ): Boolean =
                oldEntity.id == newEntity.id


            override fun areContentsTheSame(
                oldEntity: ArticleEntity,
                newEntity: ArticleEntity
            ): Boolean =
                oldEntity == newEntity

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            tvArticleTitle.text = item?.title
            tvArticleCategory.text = item?.category

            Glide.with(root)
                .load(item?.image)
                .into(ivArticle)

            root.setOnClickListener {
                onClickListener?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class ViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)
}