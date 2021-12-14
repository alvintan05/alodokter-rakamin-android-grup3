package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grup3.alodokter_rakamin_android_grup3.adapters.CurrentBookingRVAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentCurrentBookingBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.CurrentBookItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrentBookingFragment : BaseFragment<FragmentCurrentBookingBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCurrentBookingBinding =
        FragmentCurrentBookingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listCurrentBook = listOf(
            CurrentBookItem(
                "Senin, 06 Desember 2021 15:00",
                "Dr. Budi",
                "Spesialis Tulang",
                "Rs. Siloam",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            ),
            CurrentBookItem(
                "Senin, 06 Desember 2021 15:00",
                "Dr. Budi",
                "Spesialis Tulang",
                "Rs. Siloam",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            ),
            CurrentBookItem(
                "Senin, 06 Desember 2021 15:00",
                "Dr. Budi",
                "Spesialis Tulang",
                "Rs. Siloam",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            )
        )
        val currentBookingRVAdapter = CurrentBookingRVAdapter()
        currentBookingRVAdapter.setItem(listCurrentBook)
        binding.rvCurrentBooking.setHasFixedSize(true)
        binding.rvCurrentBooking.adapter = currentBookingRVAdapter
        currentBookingRVAdapter.onClickListener = {
            startActivity(Intent(activity, DetailBookingActivity::class.java))
        }
    }
}