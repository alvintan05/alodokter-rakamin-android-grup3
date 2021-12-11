package com.grup3.alodokter_rakamin_android_grup3.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySignInBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword.ForgotPasswordActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.profile.ProfileActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : BaseActivity<ActivitySignInBinding>() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var sbSignIn: Snackbar

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAlertDialog()

        binding.tvBelumPunyaAkun.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.tvLupaKataSandi.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnMasuk.setOnClickListener {
            checkInput()
        }

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    data?.let { viewModel.saveUserLoginSession(it.id, it.token)
                        val intent = Intent(this, ProfileActivity::class.java)
                        intent.putExtra("id", it.id)
                        startActivity(intent)
                    }
                    finish()
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it) }
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

    private fun setupSnackbar(message: String) {
        sbSignIn = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        sbSignIn.show()
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

//    private fun setupKataSandiListener() {
//        binding.etKataSandi.doAfterTextChanged {
//            val inputKataSandi = it.toString().trim()
//            when {
//                inputKataSandi.isEmpty() -> {
//                    binding.tlKataSandi.error = "Kata sandi tidak boleh kosong"
//                }
//            }
//        }
//    }

//    private fun setupEmailListener() {
//        binding.etEmail.doAfterTextChanged {
//            val inputEmail = it.toString().trim()
//            when {
//                inputEmail.isEmpty() -> {
//                    binding.tlEmail.error = "Email tidak boleh kosong"
//                }
//                Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
//                    binding.tlEmail.isErrorEnabled = false
//                }
//                else -> {
//                    binding.tlEmail.error = "Format email salah"
//                }
//            }
//        }
//    }

    private fun checkInput() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etKataSandi.text.toString().trim()

        if (viewModel.checkInput(email, password)) {
            signInUser(email, password)
        } else {
            setupSnackbar(resources.getString(R.string.message_fix_input_data))
        }
    }
}