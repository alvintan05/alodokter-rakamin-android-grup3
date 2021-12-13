package com.grup3.alodokter_rakamin_android_grup3.ui.profile.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityChangePasswordBinding =
        ActivityChangePasswordBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbChangePassword)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}