package com.grup3.alodokter_rakamin_android_grup3.ui.booking.choosedate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentChooseDateBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseDateFragment : BaseFragment<FragmentChooseDateBinding>() {

    // this is for access same view model with activity contaienr
    private val viewModel: BookingSharedViewModel by activityViewModels()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChooseDateBinding = FragmentChooseDateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setPosition(1)
        binding.btnNext.setOnClickListener {
            val action = ChooseDateFragmentDirections.actionChooseDateFragmentToFillDataFragment()
            findNavController().navigate(action)
        }
    }
}