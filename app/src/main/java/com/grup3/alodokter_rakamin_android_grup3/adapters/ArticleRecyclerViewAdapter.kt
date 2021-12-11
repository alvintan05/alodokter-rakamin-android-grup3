package com.grup3.alodokter_rakamin_android_grup3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemArticleBinding

class ArticleRecyclerViewAdapter : RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {

    var onClickListener : ((item: Any?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            onClickListener?.invoke(null)
        }
    }

    override fun getItemCount(): Int = 10

    inner class ViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

}