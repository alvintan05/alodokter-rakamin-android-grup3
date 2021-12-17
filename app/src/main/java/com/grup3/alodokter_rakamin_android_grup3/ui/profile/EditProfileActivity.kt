package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityEditProfileBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var dateFormatter: SimpleDateFormat
    private lateinit var datePicker: DatePickerDialog
    private lateinit var calendar: Calendar
    private var userData: UserEntity? = null

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityEditProfileBinding =
        ActivityEditProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbEditProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        userData = intent.getParcelableExtra(DetailProfileActivity.PROFILE_KEY)
        userData?.let { initUserData(it) }
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
                    this.setResult(Activity.RESULT_OK)
                    finish()
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it, false) }
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
        return EditProfileBody(
            fullname = binding.tilName.editText?.text.toString(),
            phone = binding.tilPhoneNumber.editText?.text.toString(),
            birthDate = binding.etTanggalLahir.text.toString(),
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initUserData(userEntity: UserEntity) {
        binding.tilName.editText?.setText(userEntity.fullname)
        binding.tilPhoneNumber.editText?.setText(userEntity.phone)
        binding.etTanggalLahir.setText(userEntity.birthDate)
        binding.etNomorKtp.setText(userEntity.identityNumber)
        binding.etAlamat.setText(userEntity.address)
    }

}