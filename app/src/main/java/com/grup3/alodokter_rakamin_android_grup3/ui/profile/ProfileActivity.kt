package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityProfileBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.profile.changepassword.ChangePasswordActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfileBinding =
        ActivityProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar()
        showData()

        binding.btnMyData.setOnClickListener {
            startActivity(Intent(this, DetailProfileActivity::class.java))
        }
        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding.btnSignOut.setOnClickListener { showLogoutConfirmation() }
    }

    private fun setupToolbar() {
        binding.tbProfile.setNavigationIcon(R.drawable.ic_back_arrow)
        setSupportActionBar(binding.tbProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showData() {
        // TODO: Get data from API / Shared Pref
        binding.tvUserName.text = "Dummy Name"

        Glide.with(this)
            .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
            .circleCrop()
            .into(binding.ivProfilePicture)
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.konfirmasi_logout))
            .setMessage(getString(R.string.profile_logout_confirmation_message))
            .setPositiveButton(getString(R.string.keluar)) { dialog, _ ->
                // TODO: Add logout function, Remove session first
                viewModel.removeUserLoginSession()
                dialog.cancel()
                this@ProfileActivity.finish()

            }
            .setNegativeButton(getString(R.string.batal), null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}