package com.grup3.alodokter_rakamin_android_grup3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemHistoryBookingBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.HistoryBookItem

class HistoryBookingRVAdapter : RecyclerView.Adapter<HistoryBookingRVAdapter.Viewholder>() {

    private val itemList = arrayListOf<HistoryBookItem>()
    var onClickListener: ((item: HistoryBookItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder =
        Viewholder(
            ItemHistoryBookingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    fun setItem(list: List<HistoryBookItem>) {
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
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

    inner class Viewholder(val binding: ItemHistoryBookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}