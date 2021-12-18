package com.grup3.alodokter_rakamin_android_grup3.ui.forgotpassword.emailconfirmation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentCreateNewPasswordBinding

class EmailSentFragment : BaseFragment<FragmentCreateNewPasswordBinding>() {

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCreateNewPasswordBinding =
        FragmentCreateNewPasswordBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenEmail.setOnClickListener {
            openEmail()
        }
        binding.tvBackToLogin.setOnClickListener {
            activity?.finish()
        }
    }

    private fun openEmail() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_EMAIL)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        activity?.startActivity(intent)
    }

}