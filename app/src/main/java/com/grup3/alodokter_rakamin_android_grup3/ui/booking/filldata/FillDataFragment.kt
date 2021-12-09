package com.grup3.alodokter_rakamin_android_grup3.ui.booking.filldata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentFillDataBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSharedViewModel
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.choosedate.ChooseDateFragmentDirections

class FillDataFragment : BaseFragment<FragmentFillDataBinding>() {

    // this is for access same view model with activity contaienr
    private val viewModel: BookingSharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFillDataBinding = FragmentFillDataBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPosition(2)
        binding.btnNext.setOnClickListener {
            val action = FillDataFragmentDirections.actionFillDataFragmentToConfirmationDataFragment()
            findNavController().navigate(action)
        }
    }
}