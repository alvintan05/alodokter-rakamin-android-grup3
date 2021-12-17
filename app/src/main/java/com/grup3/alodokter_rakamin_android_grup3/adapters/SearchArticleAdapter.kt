package com.grup3.alodokter_rakamin_android_grup3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemSearchArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity

class SearchArticleAdapter : RecyclerView.Adapter<SearchArticleAdapter.Viewholder>() {

    private val itemList = arrayListOf<ArticleEntity>()
    var onClickListener: ((item: ArticleEntity) -> Unit)? = null

    inner class Viewholder(val binding: ItemSearchArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder =
        Viewholder(
            ItemSearchArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun setItem(list: List<ArticleEntity>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = itemList[position]

        holder.binding.tvArticleTitle.text = item.title
        holder.binding.tvArticleCategory.text = item.category
        Glide.with(holder.binding.root)
            .load(item.image)
            .fitCenter()
            .into(holder.binding.ivArticle)
        holder.binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int = itemList.size
}