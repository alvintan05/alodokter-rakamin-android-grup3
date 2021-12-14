package com.grup3.alodokter_rakamin_android_grup3.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityBookingProcessBinding
import com.kofigyan.stateprogressbar.StateProgressBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookingProcessActivity : BaseActivity<ActivityBookingProcessBinding>() {

    private val viewModel: BookingSharedViewModel by viewModels()
    private lateinit var descriptionArray: Array<String>

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityBookingProcessBinding =
        ActivityBookingProcessBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        descriptionArray = arrayOf(
            resources.getString(R.string.title_choose_date),
            resources.getString(R.string.title_fill_data),
            resources.getString(R.string.title_confirmation_data)
        )

        setupToolbar()
        setupStateProgress()
        checkCurrentStateProgress()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbBookingProcess)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupStateProgress() {
        binding.bookingStateProgress.setStateDescriptionData(descriptionArray)
    }

    private fun checkCurrentStateProgress() {
        viewModel.currentStatePosition.observe(this, { value ->
            when (value) {
                1 -> {
                    binding.bookingStateProgress.setCurrentStateNumber(StateProgressBar.StateNumber.ONE)
                    binding.tbBookingProcess.title = descriptionArray[0]
                }
                2 -> {
                    binding.bookingStateProgress.setCurrentStateNumber(StateProgressBar.StateNumber.TWO)
                    binding.tbBookingProcess.title = descriptionArray[1]
                }
                else -> {
                    binding.bookingStateProgress.setCurrentStateNumber(StateProgressBar.StateNumber.THREE)
                    binding.tbBookingProcess.title = descriptionArray[2]
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}