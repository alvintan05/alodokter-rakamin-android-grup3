package com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword.emailconfirmation

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentEmailConfirmationBinding

class EmailConfirmationFragment : BaseFragment<FragmentEmailConfirmationBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEmailConfirmationBinding =
        FragmentEmailConfirmationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEmailListener()
        binding.btnSendEmail.setOnClickListener {
            // Add API call to send reset email

            val action =
                EmailConfirmationFragmentDirections.actionForgotPasswordFragmentToEmailSentFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupEmailListener() {
        binding.btnSendEmail.isEnabled = false
        binding.edtEmail.doAfterTextChanged {
            val inputEmail = binding.edtEmail.text.toString().trim()

            when {
                inputEmail.isEmpty() -> {
                    binding.tilEmail.error = getString(R.string.forgot_pass_error_empty_email)
                    binding.btnSendEmail.isEnabled = false
                }
                Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
                    binding.tilEmail.isErrorEnabled = false
                    binding.btnSendEmail.isEnabled = true
                }
                else -> {
                    binding.tilEmail.error = getString(R.string.forgot_pass_error_invalid_email)
                    binding.btnSendEmail.isEnabled = false
                }
            }
        }
    }

}