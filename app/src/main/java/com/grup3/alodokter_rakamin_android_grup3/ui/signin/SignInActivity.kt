package com.grup3.alodokter_rakamin_android_grup3.ui.signin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySignInBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword.ForgotPasswordActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var signUpResultLauncher: ActivityResultLauncher<Intent>

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAlertDialog()

        signUpResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    setupSnackbar(getString(R.string.successful_registration), true)
                }
            }

        binding.tvBelumPunyaAkun.setOnClickListener {
            signUpResultLauncher.launch(Intent(this, SignUpActivity::class.java))
        }

        binding.tvLupaKataSandi.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnMasuk.setOnClickListener {
            hideKeyboard()
            checkInput()
        }

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    data?.let { viewModel.saveUserLoginSession(it.id, it.token) }
                    Toast.makeText(
                        this,
                        resources.getString(R.string.toast_login_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it, false) }
                }
            }
        })

        binding.etEmail.doAfterTextChanged {
            viewModel.emailValidation(it.toString())

            viewModel.isEmailValid.observe(this, { status ->
                if (!status) {
                    binding.tlEmail.isErrorEnabled = true
                    binding.tlEmail.error =
                        resources.getString(R.string.forgot_pass_error_invalid_email)
                } else {
                    binding.tlEmail.isErrorEnabled = false
                }
            })
        }

        binding.etKataSandi.doAfterTextChanged {
            viewModel.passwordValidation(it.toString())

            viewModel.isPasswordValid.observe(this, { status ->
                if (!status) {
                    binding.tlKataSandi.isErrorEnabled = true
                    binding.tlKataSandi.error =
                        resources.getString(R.string.message_password_less_than_six)
                } else {
                    binding.tlKataSandi.isErrorEnabled = false
                }
            })
        }
    }

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

    private fun signInUser(email: String, password: String) {
        viewModel.signInUser(LoginBody(email, password))

        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun checkInput() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etKataSandi.text.toString().trim()

        if (viewModel.checkInput(email, password)) {
            signInUser(email, password)
        } else {
            setupSnackbar(resources.getString(R.string.message_fix_input_data), false)
        }
    }
}