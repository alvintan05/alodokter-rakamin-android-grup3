package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.CurrentBookingRVAdapter
import com.grup3.alodokter_rakamin_android_grup3.adapters.HistoryBookingRVAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentHistoryBookingBinding
import com.grup3.alodokter_rakamin_android_grup3.models.entity.CurrentBookItem
import com.grup3.alodokter_rakamin_android_grup3.models.entity.HistoryBookItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryBookingFragment : BaseFragment<FragmentHistoryBookingBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHistoryBookingBinding =
        FragmentHistoryBookingBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val listHistoryBook = listOf(
            HistoryBookItem(
                "Senin, 06 Desember 2021 15:00",
                "Dr. Budi",
                "Spesialis Tulang",
                "Rs. Siloam",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            ),
            HistoryBookItem(
                "Senin, 06 Desember 2021 15:00",
                "Dr. Budi",
                "Spesialis Tulang",
                "Rs. Siloam",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            ),
            HistoryBookItem(
                "Senin, 06 Desember 2021 15:00",
                "Dr. Budi",
                "Spesialis Tulang",
                "Rs. Siloam",
                "https://res.cloudinary.com/dk0z4ums3/image/upload/v1638792011/attached_image/bunda-ini-tips-mengatasi-rasa-sakit-saat-bab-setelah-melahirkan-0-alodokter.jpg"
            )
        )
        val historyBookingRVAdapter = HistoryBookingRVAdapter()
        historyBookingRVAdapter.setItem(listHistoryBook)
        binding.rvHistoryBooking.setHasFixedSize(true)
        binding.rvHistoryBooking.adapter = historyBookingRVAdapter
        historyBookingRVAdapter.onClickListener = {
            startActivity(Intent(activity, DetailBookingActivity::class.java))
        }
    }
}