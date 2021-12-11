package com.grup3.alodokter_rakamin_android_grup3.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySignUpBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInActivity
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
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            viewModel.konfirmasiPasswordValidation(password ,it.toString())
            viewModel.isKonfirmasiPasswordValid.observe(this, { status ->
                if (!status) {
                    binding.tlKonfirmasiKataSandi.isErrorEnabled = true
                    binding.tlKonfirmasiKataSandi.error = "Konfirmasi Kata sandi tidak boleh kurang dari 6 dan harus sama dengan kata sandi"
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

        //cek jenis kelamin
//        binding.rgJenisKelamin.setOnCheckedChangeListener { radioGroup, i ->
//            var rb: RadioButton = findViewById<RadioButton>(i)
//            if (rb != null){
//                val jenisKelamin = rb.text.toString()
//            }
//        }

        if(viewModel.checkInput(namaLengkap, email, password, konfirmasiPassword)) {

            signUp()
       //Toast.makeText(this, "Sukses", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
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
            firstName = binding.etNamaLengkap.text.toString(),
            password = binding.etKataSandi.text.toString(),
            gender = rbGender?.text.toString(),
        )
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

//    private fun setupKataSandiListener() {
//        binding.etKataSandi.addTextChangedListener(object: TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                val inputKataSandi = binding.etKataSandi.text.toString().trim()
//                when {
//                    inputKataSandi.isEmpty() -> {
//                        binding.tlKataSandi.error =  "Kata sandi tidak boleh kosong"
//                    }
//                }
//            }
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//    }

//    private fun setupEmailListener() {
//        binding.etEmail.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                val inputEmail = binding.etEmail.text.toString().trim()
//                when {
//                    inputEmail.isEmpty() -> {
//                        binding.tlEmail.error = "Email tidak boleh kosong"
//                    }
//                    Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
//                        binding.tlEmail.isErrorEnabled = false
//                    }
//                    else -> {
//                        binding.tlEmail.error = "Format email salah"
//                    }
//                }
//            }
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//    }

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