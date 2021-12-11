package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityEditProfileBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var sbEditProfile: Snackbar
    private lateinit var dateFormatter: SimpleDateFormat
    private lateinit var datePicker: DatePickerDialog
    private lateinit var calendar: Calendar

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityEditProfileBinding =
        ActivityEditProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbEditProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAlertDialog()
        setupDatePickerDialog()

        binding.etTanggalLahir.inputType = InputType.TYPE_NULL
        binding.etTanggalLahir.setOnClickListener {
            datePicker.show()
        }

        binding.btnSimpan.setOnClickListener {
            editProfile(getProfileBody())
        }

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    Toast.makeText(
                        this,
                        getString(R.string.message_update_data_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it) }
                }
            }
        })
    }

    private fun setupDatePickerDialog() {
        dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        calendar = Calendar.getInstance()

        datePicker = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                val newDate: Calendar = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                binding.etTanggalLahir.setText(dateFormatter.format(newDate.time).toString())
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate = System.currentTimeMillis()
    }

    private fun editProfile(editProfileBody: EditProfileBody) {
        viewModel.editProfile(editProfileBody)

        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun getProfileBody(): EditProfileBody {
        val rbGender = findViewById<RadioButton>(binding.rgJenisKelamin.checkedRadioButtonId)
        return EditProfileBody(
            null,
            firstName = binding.tilName.editText?.text.toString(),
            phone = binding.tilPhoneNumber.editText?.text.toString(),
            birthDate = binding.etTanggalLahir.text.toString(),
            gender = rbGender?.text.toString(),
            identityNumber = binding.tilNomorKtp.editText?.text.toString(),
            address = binding.tilAlamat.editText?.text.toString()
        )
    }

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

    private fun setupSnackbar(message: String) {
        sbEditProfile = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        sbEditProfile.show()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}