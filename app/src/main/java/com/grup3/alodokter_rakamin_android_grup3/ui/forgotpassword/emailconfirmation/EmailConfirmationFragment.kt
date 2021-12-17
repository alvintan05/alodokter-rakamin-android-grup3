package com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword.emailconfirmation

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentEmailConfirmationBinding

class EmailConfirmationFragment : BaseFragment<FragmentEmailConfirmationBinding>() {

    private lateinit var timer: CountDownTimer

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailConfirmationBinding =
        FragmentEmailConfirmationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEmailListener()
        binding.btnSendOtp.setOnClickListener {
            // TODO: Add API call for OTP function here
            showSnackBar(true)
            showOTPLayout()
            startOTPResendTimer()
        }
        binding.tvOtpResend.setOnClickListener {
            // TODO: Add resend OTP function here
            it.visibility = View.GONE
            startOTPResendTimer()
        }
        binding.btnVerifyOtp.setOnClickListener {
            // TODO: Add OTP verification function here

            timer.cancel()
            val action =
                EmailConfirmationFragmentDirections.actionForgotPasswordFragmentToCreateNewPasswordFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupEmailListener() {
        binding.btnSendOtp.isEnabled = false
        binding.edtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputEmail = binding.edtEmail.text.toString().trim()

                when {
                    inputEmail.isEmpty() -> {
                        binding.tilEmail.error = getString(R.string.forgot_pass_error_empty_email)
                        binding.btnSendOtp.isEnabled = false
                    }
                    Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
                        binding.tilEmail.isErrorEnabled = false
                        binding.btnSendOtp.isEnabled = true
                    }
                    else -> {
                        binding.tilEmail.error = getString(R.string.forgot_pass_error_invalid_email)
                        binding.btnSendOtp.isEnabled = false
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun showSnackBar(success: Boolean) {
        val message =
            if (success) getString(R.string.forgot_pass_message_otp_delivered)
            else getString(R.string.forgot_pass_message_email_not_found)

        val color = if (success) context?.let {
            ContextCompat.getColor(it, R.color.success)
        } else context?.let {
            ContextCompat.getColor(it, R.color.error)
        }

        val sbEmailOTP = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
        color?.let { sbEmailOTP.setBackgroundTint(it) }
        sbEmailOTP.show()
    }

    private fun showOTPLayout() {
        binding.edtEmail.isEnabled = false
        binding.btnSendOtp.visibility = View.GONE
        binding.llOtpField.visibility = View.VISIBLE
        setupOtpPinViewListener()
    }

    private fun setupOtpPinViewListener() {
        binding.btnVerifyOtp.isEnabled = false
        binding.pvOtp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputOTP = binding.pvOtp.text.toString().trim()
                binding.btnVerifyOtp.isEnabled = inputOTP.length == 6
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun startOTPResendTimer() {
        binding.tvOtpResendTimer.visibility = View.VISIBLE
        timer = object : CountDownTimer(60000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                binding.tvOtpResendTimer.text =
                    getString(R.string.forgot_pass_otp_resend_timer, millisUntilFinished / 1000)
            }

            override fun onFinish() {
                binding.tvOtpResendTimer.visibility = View.GONE
                binding.tvOtpResend.visibility = View.VISIBLE
            }
        }.start()
    }

    override fun onResume() {
        super.onResume()
        binding.pvOtp.text = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (::timer.isInitialized) {
            timer.cancel()
        }
    }
}