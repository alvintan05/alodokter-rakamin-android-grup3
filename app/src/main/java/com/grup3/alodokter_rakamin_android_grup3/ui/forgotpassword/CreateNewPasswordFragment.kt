package com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentCreateNewPasswordBinding

class CreateNewPasswordFragment : BaseFragment<FragmentCreateNewPasswordBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateNewPasswordBinding =
        FragmentCreateNewPasswordBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPasswordConfirmListener()
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.btnSavePassword.setOnClickListener {
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()

            // TODO: Send password to API and navigate to login page
        }
    }

    private fun setupPasswordConfirmListener() {
        binding.edtPasswordConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val inputPassword = binding.edtPasswordConfirm.text.toString().trim()
                val inputConfirmPassword = binding.edtPasswordConfirm.text.toString().trim()

                if (inputPassword != inputConfirmPassword) {
                    binding.tilPasswordConfirm.error =
                        getString(R.string.forgot_pass_error_confirmation_mismatch)
                } else {
                    binding.tilPasswordConfirm.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}