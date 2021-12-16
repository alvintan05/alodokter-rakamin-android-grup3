package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityProfilDoctorBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilDoctorActivity : BaseActivity<ActivityProfilDoctorBinding>() {

    private val viewModel: ProfilDoctorViewModel by viewModels()
    private lateinit var builder: AlertDialog.Builder

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfilDoctorBinding =
        ActivityProfilDoctorBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbProfilDokter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profil Dokter"
        builder = AlertDialog.Builder(this)

        checkUserLoginStatus()
        setupScheduleDoctor()

    }

    private fun setupScheduleDoctor() {
        val rvScheduleDoctor = binding.rvJadwalPraktik
        rvScheduleDoctor.setHasFixedSize(true)

        var list = ArrayList<ScheduleDoctor>()
        list.add(ScheduleDoctor("Senin", "19.00 - 22.00"))
        list.add(ScheduleDoctor("Selasa", "18.00 - 20.00"))
        list.add(ScheduleDoctor("Kamis", "19.00 - 22.00"))
        list.add(ScheduleDoctor("Sabtu", "13.00 - 16.00"))

        rvScheduleDoctor.layoutManager = LinearLayoutManager(this)
        val listScheduleDoctor = ScheduleDoctordapter(list)
        rvScheduleDoctor.adapter = listScheduleDoctor
    }

    private fun checkUserLogin() {

        val view = View.inflate(this@ProfilDoctorActivity, R.layout.custom_alert_dialog_login, null)

        val builder = AlertDialog.Builder(this@ProfilDoctorActivity)
        builder.setView(view)

        val dialog = builder.setCancelable(false).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.findViewById<TextView>(R.id.btn_redirect_masuk).setOnClickListener {
            startActivity(Intent(this@ProfilDoctorActivity, SignInActivity::class.java))
        }

        view.findViewById<TextView>(R.id.btn_nanti).setOnClickListener {
            dialog.dismiss()
            finish()
        }
    }

    private fun checkUserLoginStatus() {
        if (!viewModel.getUserLoginStatus()) checkUserLogin()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
