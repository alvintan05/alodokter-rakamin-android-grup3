package com.grup3.alodokter_rakamin_android_grup3.ui.booking.confirmation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentConfirmationDataBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSharedViewModel


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
    }
}