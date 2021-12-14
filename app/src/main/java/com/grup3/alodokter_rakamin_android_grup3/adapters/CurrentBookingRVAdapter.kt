package com.grup3.alodokter_rakamin_android_grup3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemCurrentBookingBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.CurrentBookItem

class CurrentBookingRVAdapter : RecyclerView.Adapter<CurrentBookingRVAdapter.ViewHolder>() {

    private val itemList = arrayListOf<CurrentBookItem>()
    var onClickListener: ((item: CurrentBookItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemCurrentBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun setItem(list: List<CurrentBookItem>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        holder.binding.tvBookingDateTime.text = item.bookingDate
        holder.binding.tvDoctorName.text = item.doctorName
        holder.binding.tvDoctorSpecialist.text = item.doctorSpecialist
        holder.binding.tvDoctorClinic.text = item.doctorClinic
        Glide.with(holder.binding.root)
            .load(item.doctorImage)
            .fitCenter()
            .circleCrop()
            .into(holder.binding.ivDoctor)
        holder.binding.root.setOnClickListener {
            onClickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    inner class ViewHolder(val binding: ItemCurrentBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}