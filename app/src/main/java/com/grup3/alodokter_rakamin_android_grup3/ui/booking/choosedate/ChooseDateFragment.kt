package com.grup3.alodokter_rakamin_android_grup3.ui.booking.choosedate

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentChooseDateBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.booking.BookingSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ChooseDateFragment : BaseFragment<FragmentChooseDateBinding>() {

    // this is for access same view model with activity contaienr
    private val viewModel: BookingSharedViewModel by activityViewModels()
    private var practiceDateFormatUI: String = ""
    private var practiceDateFormatApi: String = ""

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChooseDateBinding = FragmentChooseDateBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        viewModel.setPosition(1)
        setupSpinner()

        Glide.with(this)
            .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
            .circleCrop()
            .into(binding.ivDoctor)

        binding.etPracticeDate.inputType = InputType.TYPE_NULL
        binding.etPracticeDate.setOnClickListener {
            showDatePickerDialog(year, month, day)
        }
        binding.btnNext.setOnClickListener {
            val action = ChooseDateFragmentDirections.actionChooseDateFragmentToFillDataFragment()
            findNavController().navigate(action)
        }
    }

    private fun showDatePickerDialog(year: Int, month: Int, day: Int) {
        val dpd = DatePickerDialog(
            this.requireActivity(),
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                practiceDateFormatUI = "$mDay/${mMonth+1}/$mYear"
                practiceDateFormatApi = "$mYear" + "-" + "${mMonth+1}" + "-" + "$mDay"
                binding.etPracticeDate.setText(practiceDateFormatUI)
            },
            year,
            month,
            day
        )
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }

    private fun setupSpinner() {
        val arrayAdapter =
            ArrayAdapter(
                requireActivity(),
                R.layout.style_spinner,
                resources.getStringArray(R.array.practice_hour_array)
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spPracticeHour.adapter = arrayAdapter
    }
}