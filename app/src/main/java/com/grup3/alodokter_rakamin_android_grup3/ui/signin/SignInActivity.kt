package com.grup3.alodokter_rakamin_android_grup3.ui.signin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
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

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySignInBinding =
        ActivitySignInBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEmailListener()
        setupKataSandiListener()

        binding.tvBelumPunyaAkun.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.tvLupaKataSandi.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnMasuk.setOnClickListener {
            // Change Later with API Function
            signInUser()
        }
    }

    private fun signInUser() {
        viewModel.signInUser(
            LoginBody("budi@gmail.com", "123456")
        )
        viewModel.loading.observe(this, {
            if (it) {
                Log.d("Sign In", "Loading...")
            } else {
                Log.d("Sign In", "Loading Stop...")
            }
        })

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    Log.d("Sign In", "Success")
                    viewModel.saveUserLoginSession()
                    finish()
                }
                else ->{
                    Log.d("Sign In", "Error")
                }
            }
        })
    }

    private fun setupKataSandiListener() {
        binding.etKataSandi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputKataSandi = binding.etKataSandi.text.toString().trim()
                when {
                    inputKataSandi.isEmpty() -> {
                        binding.tlKataSandi.error = "Kata sandi tidak boleh kosong"
                    }

                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun setupEmailListener() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputEmail = binding.etEmail.text.toString().trim()

                when {
                    inputEmail.isEmpty() -> {
                        binding.tlEmail.error = "Email tidak boleh kosong"
                    }
                    Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
                        binding.tlEmail.isErrorEnabled = false
                    }
                    else -> {
                        binding.tlEmail.error = "Format email salah"
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }
}