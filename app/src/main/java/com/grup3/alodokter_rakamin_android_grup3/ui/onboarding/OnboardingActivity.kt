package com.grup3.alodokter_rakamin_android_grup3.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.ViewPagerAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityOnboardingBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingActivity : BaseActivity<ActivityOnboardingBinding>() {

    private var pagerPosition = 0
    private val viewModel: OnboardingViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityOnboardingBinding =
        ActivityOnboardingBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewPager()

        binding.btnOnboarding.setOnClickListener {
            if (pagerPosition == 2) {
                viewModel.updateFirstTimeInstallStatusToFalse()
                startActivity(Intent(this, IndexActivity::class.java))
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