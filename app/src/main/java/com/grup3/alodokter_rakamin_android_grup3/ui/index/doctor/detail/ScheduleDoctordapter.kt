package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grup3.alodokter_rakamin_android_grup3.R

//class ScheduleDoctordapter(var mContex: Context, var resources: Int, var items:List<ScheduleDoctor>)
//    : ArrayAdapter<ScheduleDoctor>(mContex, resources, items){
//
//    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
//        val layoutIntflater: LayoutInflater = LayoutInflater.from(mContex)
//        val view:View = layoutIntflater.inflate(resources, null)
//
//        val hariPraktik: TextView = view.findViewById(R.id.tv_hari_praktik)
//        val jamPraktik: TextView = view.findViewById(R.id.tv_jam_praktik)
//
//        var mItem: ScheduleDoctor = items[position]
//        hariPraktik.text = mItem.hari
//        jamPraktik.text = mItem.jam
//        return view
//    }
//}

class ScheduleDoctordapter(val list: ArrayList<ScheduleDoctor>): RecyclerView.Adapter<ScheduleDoctordapter.ListViewHolder>() {
    inner class ListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var tvHari: TextView = itemView.findViewById(R.id.tv_hari_praktik)
        var tvJam: TextView = itemView.findViewById(R.id.tv_jam_praktik)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_jadwal_praktik, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val schedule = list[position]
        holder.tvHari.text = schedule.hari
        holder.tvJam.text = schedule.jam
    }

    override fun getItemCount(): Int {
        return list.size
    }


}
