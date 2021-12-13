package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityProfilDokterBinding
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import com.grup3.alodokter_rakamin_android_grup3.ui.profile.ProfileActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfilDokterActivity : BaseActivity<ActivityProfilDokterBinding>() {

    private val viewModel: ProfilDokterViewModel by viewModels()
    private lateinit var builder: AlertDialog.Builder
    var user_id = null;

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfilDokterBinding = ActivityProfilDokterBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbProfilDokter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profil Dokter"
        builder = AlertDialog.Builder(this)

       // checkUserLogin()
        checkUserLoginStatus()

        var listView = binding.listJadwalPraktik
        var list = mutableListOf<JadwalPraktikDokter>()

        list.add(JadwalPraktikDokter("Senin", "19.00 - 22.00"))
        list.add(JadwalPraktikDokter("Selasa", "18.00 - 20.00"))
        list.add(JadwalPraktikDokter("Kamis", "19.00 - 22.00"))
        list.add(JadwalPraktikDokter("Sabtu", "13.00 - 15.00"))
        listView.adapter = JadwalPraktirDokterAdapter(this, R.layout.item_jadwal_praktik, list)
    }

    private fun checkUserLogin() {

            val view = View.inflate(this@ProfilDokterActivity, R.layout.custom_alert_dialog_error, null)

            val builder = AlertDialog.Builder(this@ProfilDokterActivity)
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            view.findViewById<TextView>(R.id.tv_yes_login).setOnClickListener {
                startActivity(Intent(this@ProfilDokterActivity, SignInActivity::class.java))
            }

            view.findViewById<TextView>(R.id.tv_no_login).setOnClickListener {
                finish()
            }
    }

    private fun checkUserLoginStatus() {
        if (viewModel.getUserLoginStatus() == false) checkUserLogin()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
