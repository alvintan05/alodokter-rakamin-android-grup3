package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory

import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityDetailBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBookingActivity : BaseActivity<ActivityDetailBookingBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        Glide.with(this)
            .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
            .circleCrop()
            .into(binding.ivDoctor)
    }

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailBookingBinding =
        ActivityDetailBookingBinding.inflate(layoutInflater)

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbDetailBooking)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}