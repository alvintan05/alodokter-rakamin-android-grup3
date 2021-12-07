package com.grup3.alodokter_rakamin_android_grup3.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.grup3.alodokter_rakamin_android_grup3.ui.onboarding.OnboardingSliderFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf(
        OnboardingSliderFragment.newInstance("0"),
        OnboardingSliderFragment.newInstance("1"),
        OnboardingSliderFragment.newInstance("2")
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}