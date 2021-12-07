package com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.btnSavePassword.setOnClickListener {
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()

            // TODO: Send password to API and navigate to login page
        }
        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}