package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityProfileBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.ui.profile.changepassword.ChangePasswordActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity<ActivityProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    private lateinit var sbProfile : Snackbar



    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfileBinding =
        ActivityProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getStringExtra("id")

        id?.let { showData(it) }
        setupToolbar()
        setupAlertDialog()

        binding.btnMyData.setOnClickListener {
            startActivity(Intent(this, DetailProfileActivity::class.java))
        }
        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
        binding.btnSignOut.setOnClickListener { showLogoutConfirmation() }

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    val data = resource.data
                    data?.let { Log.d("RESPONEAPA", it.toString())}

                }
                is Resource.Error -> {
                    resource.error?.let {setupSnackbar(it)}
                }
            }
        })
    }

    private fun setupToolbar() {
        binding.tbProfile.setNavigationIcon(R.drawable.ic_back_arrow)
        setSupportActionBar(binding.tbProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupSnackbar(message: String) {
        sbProfile = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        sbProfile.show()
    }

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

    private fun showData(id: String) {
        // TODO: Get data from API / Shared Pref
    //    binding.tvUserName.text = "Dummy Name"

        viewModel.getDetailProfile(id)


        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })




        /*Glide.with(this)
            .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
            .circleCrop()
            .into(binding.ivProfilePicture)*/
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