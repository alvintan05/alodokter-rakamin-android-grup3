package com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : BaseFragment<FragmentForgotPasswordBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentForgotPasswordBinding =
        FragmentForgotPasswordBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEmailListener()

        binding.btnSendOtp.setOnClickListener {
            showSnackBar(true)
        }
    }

    private fun setupEmailListener() {
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputEmail = binding.edtEmail.text.toString().trim()

                when {
                    inputEmail.isEmpty() -> {
                        binding.tilEmail.error = "Email can't be empty"
                    }
                    Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
                        binding.tilEmail.isErrorEnabled = false
                    }
                    else -> {
                        binding.tilEmail.error = "Invalid email"
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun showSnackBar(success: Boolean) {
        val message = if (success) getString(R.string.message_otp_delivered)
        else getString(R.string.message_email_not_found)

        val color = if (success) context?.let { ContextCompat.getColor(it, R.color.success) }
        else context?.let { ContextCompat.getColor(it, R.color.error) }

        val sbEmailOTP = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
        color?.let { sbEmailOTP.setBackgroundTint(it) }
        sbEmailOTP.show()
    }
}