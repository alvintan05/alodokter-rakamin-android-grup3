package com.grup3.alodokter_rakamin_android_grup3.ui.booking.filldata

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentFillDataBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSharedViewModel
import java.util.*

class FillDataFragment : BaseFragment<FragmentFillDataBinding>() {

    // this is for access same view model with activity contaienr
    private val viewModel: BookingSharedViewModel by activityViewModels()
    private var birthDateFormatUI: String = ""
    private var birthDateFormatApi: String = ""

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFillDataBinding = FragmentFillDataBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setPosition(2)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.edtTanggalLahir.inputType = InputType.TYPE_NULL
        binding.edtTanggalLahir.setOnClickListener {
            showDatePickerDialog(year, month, day)
        }

        binding.btnNext.setOnClickListener {
            val action =
                FillDataFragmentDirections.actionFillDataFragmentToConfirmationDataFragment()
            findNavController().navigate(action)
        }
    }

    private fun showDatePickerDialog(year: Int, month: Int, day: Int) {
        val dpd = DatePickerDialog(
            this.requireActivity(),
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                birthDateFormatUI = "$mDay/${mMonth+1}/$mYear"
                birthDateFormatApi = "$mYear" + "-" + "${mMonth+1}" + "-" + "$mDay"
                binding.edtTanggalLahir.setText(birthDateFormatUI)
            },
            year,
            month,
            day
        )
        dpd.show()
    }
}