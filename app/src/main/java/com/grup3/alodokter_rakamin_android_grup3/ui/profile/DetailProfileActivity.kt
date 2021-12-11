package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.content.Intent
import android.content.LocusId
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityDetailProfileBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInViewModel

class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding>() {

    private val viewModel: ProfileViewModel by viewModels()


    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailProfileBinding =
        ActivityDetailProfileBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbDetailProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        if (item.itemId == R.id.menu_change)
            startActivity(Intent(this, EditProfileActivity::class.java))
        return super.onOptionsItemSelected(item)
    }



    private fun showData() {
        // TODO: Get data from API / Shared Pref
        binding.tvUserName.text = "Dummy Name"


       /* Glide.with(this)
            .load(it.)
            .circleCrop()
            .into(binding.profilePicture) */

    }



}