package com.grup3.alodokter_rakamin_android_grup3.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityBookingSuccessBinding

class BookingSuccessActivity : BaseActivity<ActivityBookingSuccessBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityBookingSuccessBinding =
        ActivityBookingSuccessBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        Toast.makeText(
            this,
            resources.getString(R.string.message_alert_cannot_use_back_button),
            Toast.LENGTH_SHORT
        ).show()
    }
}