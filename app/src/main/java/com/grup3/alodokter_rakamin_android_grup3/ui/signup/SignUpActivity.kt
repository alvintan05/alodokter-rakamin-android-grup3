package com.grup3.alodokter_rakamin_android_grup3.ui.signup

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySignUpBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {

    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var sbSignIn: Snackbar


    override fun inflateLayout(layoutInflater: LayoutInflater):
            ActivitySignUpBinding = ActivitySignUpBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAlertDialog()

        setSupportActionBar(binding.tbDaftar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Daftar"

//        setupEmailListener()
//        setupKataSandiListener()

        binding.btnDaftar.setOnClickListener {
            checkInput()
        }

        binding.etEmail.doAfterTextChanged {
            viewModel.emailValidation((it.toString()))
            viewModel.isEmailValid.observe(this, { status ->
                if (!status) {
                    binding.tlEmail.isErrorEnabled = true
                    binding.tlEmail.error = "Format email salah"
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
                    binding.tlKataSandi.error = "Kata sandi tidak boleh kurang dari 6"
                } else {
                    binding.tlKataSandi.isErrorEnabled = false
                }
            })
        }

        binding.etKonfirmasiKataSandi.doAfterTextChanged {
            val password = binding.etKataSandi.text.toString().trim()
            viewModel.konfirmasiPasswordValidation(password, it.toString())
            viewModel.isKonfirmasiPasswordValid.observe(this, { status ->
                if (!status) {
                    binding.tlKonfirmasiKataSandi.isErrorEnabled = true
                    binding.tlKonfirmasiKataSandi.error =
                        "Konfirmasi Kata sandi tidak boleh kurang dari 6 dan harus sama dengan kata sandi"
                } else {
                    binding.tlKonfirmasiKataSandi.isErrorEnabled = false
                }

            })
        }

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    Toast.makeText(this, "Daftar Berhasil", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it) }
                }
            }
        })

    }

    private fun checkInput() {
        val namaLengkap = binding.etNamaLengkap.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etKataSandi.text.toString().trim()
        val konfirmasiPassword = binding.etKonfirmasiKataSandi.text.toString().trim()

        val isCheckedGender = binding.rbLaki.isChecked || binding.rbPerempuan.isChecked

        if (viewModel.checkInput(namaLengkap, email, password, konfirmasiPassword) && isCheckedGender) {
            signUp()
        } else {
            setupSnackbarError(resources.getString(R.string.message_fix_input_data))
        }
    }


    private fun signUp() {
        viewModel.signUpUser(getRegisterBody())
        viewModel.loading.observe(this, {
            if (it) loadingDialog.show()
            else loadingDialog.dismiss()
        })
    }

    private fun getRegisterBody(): RegisterBody {
        val rbGender = findViewById<RadioButton>(binding.rgJenisKelamin.checkedRadioButtonId)
        return RegisterBody(
            email = binding.etEmail.text.toString(),
            fullname = binding.etNamaLengkap.text.toString(),
            password = binding.etKataSandi.text.toString(),
            gender = rbGender?.text.toString(),
        )
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

    private fun setupSnackbar(message: String) {
        sbSignIn = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        sbSignIn.show()
    }

}