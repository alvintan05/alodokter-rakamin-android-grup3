package com.grup3.alodokter_rakamin_android_grup3.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupEmailListener()
        setupKataSandiListener()

    }

    private fun setupKataSandiListener() {
        binding.etKataSandi.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputKataSandi = binding.etKataSandi.text.toString().trim()
                when {
                    inputKataSandi.isEmpty() -> {
                        binding.tlKataSandi.error =  "Kata sandi tidak boleh kosong"
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