package com.grup3.alodokter_rakamin_android_grup3.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.plusAssign
import androidx.viewpager2.widget.ViewPager2
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityOnboardingBinding

class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>() {

    private var pagerPosition = 0

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityOnboardingBinding =
        ActivityOnboardingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewPager()
        binding.btnOnboarding.setOnClickListener {
            if (pagerPosition == 2) {
                finish()
            } else {
                binding.vpOnboarding.currentItem = binding.vpOnboarding.currentItem.plus(1)
            }
        }
    }

    private fun setUpViewPager() {
        val pagerAdapter = ViewPagerAdapter(this)
        binding.vpOnboarding.adapter = pagerAdapter
        binding.vpOnboarding.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                pagerPosition = position

                if (position == 2)
                    binding.btnOnboarding.text =
                        resources.getString(R.string.onboarding_button_start)
                else
                    binding.btnOnboarding.text =
                        resources.getString(R.string.onboarding_button_next)
            }
        })

        binding.vpIndicator.setViewPager2(binding.vpOnboarding)
    }
}