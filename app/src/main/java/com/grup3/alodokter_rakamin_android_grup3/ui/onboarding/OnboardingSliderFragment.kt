package com.grup3.alodokter_rakamin_android_grup3.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentOnboardingSliderBinding

private const val ARG_PARAM1 = "param1"

class OnboardingSliderFragment : BaseFragment<FragmentOnboardingSliderBinding>() {
    private var param1: String? = null

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingSliderBinding =
        FragmentOnboardingSliderBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (param1) {
            "0" -> {
                binding.ivOnboarding.setImageResource(R.drawable.onboarding_article)
                binding.tvOnboardingTitle.text = resources.getString(R.string.onboarding_article)
                binding.tvOnboardingDesc.text =
                    resources.getString(R.string.onboarding_article_desc)
            }
            "1" -> {
                binding.ivOnboarding.setImageResource(R.drawable.onboarding_booking)
                binding.tvOnboardingTitle.text = resources.getString(R.string.onboarding_booking)
                binding.tvOnboardingDesc.text =
                    resources.getString(R.string.onboarding_booking_desc)
            }
            else -> {
                binding.ivOnboarding.setImageResource(R.drawable.onboarding_consultation)
                binding.tvOnboardingTitle.text =
                    resources.getString(R.string.onboarding_consultation)
                binding.tvOnboardingDesc.text =
                    resources.getString(R.string.onboarding_consultation_desc)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            OnboardingSliderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}