package com.grup3.alodokter_rakamin_android_grup3.ui.booking.confirmation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentConfirmationDataBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSharedViewModel
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSuccessActivity


class ConfirmationDataFragment : BaseFragment<FragmentConfirmationDataBinding>() {

    // this is for access same view model with activity contaienr
    private val viewModel: BookingSharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmationDataBinding =
        FragmentConfirmationDataBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPosition(3)

        Glide.with(this)
            .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
            .circleCrop()
            .into(binding.ivDoctor)

        binding.btnMakePromise.setOnClickListener {
            startActivity(Intent(requireActivity(), BookingSuccessActivity::class.java))
            requireActivity().finish()
        }
    }
}