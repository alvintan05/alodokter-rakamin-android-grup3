package com.grup3.alodokter_rakamin_android_grup3.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.CurrentBookingFragment
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.HistoryBookingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = CurrentBookingFragment()
            1 -> fragment = HistoryBookingFragment()
        }
        return fragment as Fragment
    }
}