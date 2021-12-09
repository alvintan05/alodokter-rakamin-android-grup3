package com.grup3.alodokter_rakamin_android_grup3.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.databinding.ItemDoctorBinding

class DoctorRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<DoctorRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemDoctorBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = 14

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            // TODO: Get data from API
            binding.tvDoctorName.text = "Dr. Budi"
            binding.tvDoctorSpecialist.text = "Spesialis Tulang"
            binding.tvDoctorClinic.text = "Rs. Siloam"
            binding.tvDistance.text = context.getString(R.string.doctor_distance, 2)
            binding.tvConsultationPrice.text = context.getString(R.string.jasa_konsultasi, 300000)

            Glide.with(context)
                .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
                .circleCrop()
                .into(binding.ivDoctor)
        }
    }

    inner class ViewHolder(val binding: ItemDoctorBinding) : RecyclerView.ViewHolder(binding.root)
}