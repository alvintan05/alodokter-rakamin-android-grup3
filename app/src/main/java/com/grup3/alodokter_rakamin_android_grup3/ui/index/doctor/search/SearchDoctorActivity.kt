package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.grup3.alodokter_rakamin_android_grup3.adapters.DoctorRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySearchDoctorBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail.ProfilDoctorActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchDoctorActivity : BaseActivity<ActivitySearchDoctorBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySearchDoctorBinding =
        ActivitySearchDoctorBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbSearchDoctor)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupDoctorList()

        val querySearch = intent.getStringExtra("query")
        binding.svSearch.setQuery(querySearch, false)

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@SearchDoctorActivity, p0, Toast.LENGTH_SHORT).show()
                binding.svSearch.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // make a server call
                return false
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupDoctorList() {
        val adapter = DoctorRecyclerViewAdapter(this)
        binding.rvDoctor.setHasFixedSize(true)
        binding.rvDoctor.adapter = adapter
        adapter.onClickListener = {
            startActivity(Intent(this, ProfilDoctorActivity::class.java))
        }
    }


}