package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.StringRes
import com.google.android.material.tabs.TabLayoutMediator
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.SectionsPagerAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityListBookingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListBookingActivity : BaseActivity<ActivityListBookingBinding>() {

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_current,
            R.string.tab_text_history
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun inflateLayout(layoutInflater: LayoutInflater) =
        ActivityListBookingBinding.inflate(layoutInflater)

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.tbListBooking)
        supportActionBar?.title = getString(R.string.title_list_booking_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}