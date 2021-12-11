package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityDetailProfileBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var sbGetDetailProfile: Snackbar
    private lateinit var loadingDialog: AlertDialog
    private lateinit var userData : UserEntity

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailProfileBinding =
        ActivityDetailProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbDetailProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAlertDialog()
        showData()

        viewModel.userResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    val phoneNumber =
                        if (resource.data?.phone == "") "No HP Belum ditambahkan" else resource.data?.phone
                    val birthDate =
                        if (resource.data?.birthDate == "") "Belum ditambahkan" else resource.data?.birthDate
                    val identityNumber =
                        if (resource.data?.identityNumber == "") "Belum ditambahkan" else resource.data?.identityNumber
                    val address =
                        if (resource.data?.address == "") "Belum ditambahkan" else resource.data?.address
                    val name = resource.data?.firstName + " " + resource.data?.lastName
                    binding.tvUserName.text = name
                    binding.tvNomorTelpon.text = phoneNumber
                    binding.tvEmail.text = resource.data?.email
                    binding.tvTanggalLahir.text = birthDate
                    binding.tvNomorKtp.text = identityNumber
                    binding.tvAlamat.text = address
                    userData = UserEntity(resource.data?.id,
                        resource.data?.email,
                        resource.data?.firstName,
                        resource.data?.lastName,
                        resource.data?.birthDate,
                        resource.data?.gender,
                        resource.data?.phone,
                        resource.data?.identityNumber,
                        resource.data?.address,
                        resource.data?.city
                    )
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it) }
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_change){
            if (::userData.isInitialized) {
                // File is initialized
                val intent = Intent(this, EditProfileActivity::class.java)
                intent.putExtra("Profile_Key", userData)
                startActivity(intent)
            } else {
                // File is not initialized
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun showData() {
        // TODO: Get data from API / Shared Pref
        viewModel.getDetailProfile()

        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })

        Glide.with(this)
            .load("https://image.freepik.com/free-vector/businessman-profile-cartoon_18591-58479.jpg")
            .circleCrop()
            .into(binding.profilePicture)
    }

    private fun setupSnackbar(message: String) {
        sbGetDetailProfile = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        sbGetDetailProfile.show()
    }

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

}