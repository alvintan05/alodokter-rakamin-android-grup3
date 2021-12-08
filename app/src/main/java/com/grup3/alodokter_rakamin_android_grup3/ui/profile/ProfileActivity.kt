package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfileBinding =
        ActivityProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbProfile)

        showData()

        binding.btnMyData.setOnClickListener {
            Toast.makeText(this, "intent to my data", Toast.LENGTH_SHORT).show()
        }
        binding.btnChangePassword.setOnClickListener {
            Toast.makeText(this, "intent to change password", Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener { this.finish() }
        binding.btnSignOut.setOnClickListener { showLogoutConfirmation() }
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
                // TODO: Add logout function
                // Remove session first
                viewModel.removeUserLoginSession()
                dialog.cancel()
                this@ProfileActivity.finish()

            }
            .setNegativeButton(getString(R.string.batal), null)
            .show()
    }
}