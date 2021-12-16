package com.grup3.alodokter_rakamin_android_grup3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemSliderBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderArticleAdapter : SliderViewAdapter<SliderArticleAdapter.SliderAdapterVH>() {

    private val itemList = arrayListOf<ArticleEntity>()
    var onClickListener: ((item: ArticleEntity) -> Unit)? = null

    fun setItem(list: List<ArticleEntity>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH =
        SliderAdapterVH(
            ItemSliderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getCount(): Int = itemList.size

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val item = itemList[position]

        viewHolder.binding.tvTitle.text = item.title
        Glide.with(viewHolder.binding.root)
            .load(item.image)
            .fitCenter()
            .into(viewHolder.binding.ivAutoImageSlider)
        viewHolder.binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }

    inner class SliderAdapterVH(val binding: ItemSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root)

}