package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityProfilDokterBinding

class ProfilDokterActivity : BaseActivity<ActivityProfilDokterBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.tbProfilDokter)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profil Dokter"


        var listView = binding.listJadwalPraktik
        var list = mutableListOf<JadwalPraktikDokter>()

        list.add(JadwalPraktikDokter("Senin", "19.00 - 22.00"))
        list.add(JadwalPraktikDokter("Selasa", "18.00 - 20.00"))
        list.add(JadwalPraktikDokter("Kamis", "19.00 - 22.00"))
        list.add(JadwalPraktikDokter("Sabtu", "13.00 - 15.00"))
        listView.adapter = JadwalPraktirDokterAdapter(this, R.layout.item_jadwal_praktik, list)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityProfilDokterBinding = ActivityProfilDokterBinding.inflate(layoutInflater)


}