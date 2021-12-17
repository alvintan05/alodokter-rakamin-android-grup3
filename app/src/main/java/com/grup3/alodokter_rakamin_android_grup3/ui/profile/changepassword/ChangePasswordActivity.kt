package com.grup3.alodokter_rakamin_android_grup3.ui.profile.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityChangePasswordBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.ChangePasswordBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {

    private val viewModel: ChangePasswordViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityChangePasswordBinding =
        ActivityChangePasswordBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbChangePassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupAlertDialog()

        binding.btnUbah.setOnClickListener {
            checkInput()

            hideKeyboard()
        }

        binding.etKataSandiLama.doAfterTextChanged {
            viewModel.oldPasswordValidation(it.toString())

            viewModel.isoldPassword.observe(this, { status ->
                if (!status) {
                    binding.tlKataSandiLama.isErrorEnabled = true
                    binding.tlKataSandiLama.error =
                        resources.getString(R.string.message_password_less_than_six)
                } else {
                    binding.tlKataSandiLama.isErrorEnabled = false
                }
            })
        }

        binding.etKataSandiBaru.doAfterTextChanged {
            viewModel.passwordValidation(it.toString())

            viewModel.isPasswordValid.observe(this, { status ->
                if (!status) {
                    binding.tlKataSandiBaru.isErrorEnabled = true
                    binding.tlKataSandiBaru.error =
                        resources.getString(R.string.message_password_less_than_six)
                } else {
                    binding.tlKataSandiBaru.isErrorEnabled = false
                }
            })
        }

        binding.etKonfirmasiKataSandiBaru.doAfterTextChanged {
            val password = binding.etKataSandiBaru.text.toString().trim()
            viewModel.confirmationPasswordValidation(password, it.toString())
            viewModel.isConfirmationPasswordValid.observe(this, { status ->
                if (!status) {
                    binding.tlKonformasiKataSandiBaru.isErrorEnabled = true
                    binding.tlKonformasiKataSandiBaru.error =
                        resources.getString(R.string.message_password_doesnt_match)
                } else {
                    binding.tlKonformasiKataSandiBaru.isErrorEnabled = false
                }

            })
        }

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    Toast.makeText(
                        this,
                        resources.getString(R.string.password_change_succes),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it, false) }
                }
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

    private fun checkInput() {
        val oldPassword = binding.etKataSandiLama.text.toString().trim()
        val password = binding.etKataSandiBaru.text.toString().trim()
        val confirmationPassword = binding.etKonfirmasiKataSandiBaru.text.toString().trim()

        if (viewModel.allPasswordValidation(oldPassword, password, confirmationPassword)) {
            changePassword(
                ChangePasswordBody(
                    oldPassword, password
                )
            )
        } else {
            setupSnackbar(resources.getString(R.string.message_fix_input_data), false)
        }
    }

    private fun changePassword(changePasswordBody: ChangePasswordBody) {
        viewModel.changePassword(changePasswordBody)

        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

}