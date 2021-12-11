package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.grup3.alodokter_rakamin_android_grup3.R

class JadwalPraktirDokterAdapter(var mContex: Context, var resources: Int, var items:List<JadwalPraktikDokter>)
    : ArrayAdapter<JadwalPraktikDokter>(mContex, resources, items){

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        val layoutIntflater: LayoutInflater = LayoutInflater.from(mContex)
        val view:View = layoutIntflater.inflate(resources, null)

        val hariPraktik: TextView = view.findViewById(R.id.tv_hari_praktik)
        val jamPraktik: TextView = view.findViewById(R.id.tv_jam_praktik)

        var mItem:JadwalPraktikDokter = items[position]
        hariPraktik.text = mItem.hari
        jamPraktik.text = mItem.jam
        return view
    }
}