package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.appcompat.widget.SearchView
import com.google.android.gms.location.*
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.DoctorRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentDoctorBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.article.search.SearchArticleActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.DetailBookingActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.ListBookingActivity
import dagger.hilt.android.AndroidEntryPoint
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail.ProfilDoctorActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.search.SearchDoctorActivity
import java.util.*

const val PERMISSION_ID = 1010

@AndroidEntryPoint
class DoctorFragment : BaseFragment<FragmentDoctorBinding>() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDoctorBinding = FragmentDoctorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as IndexActivity).setSupportActionBar(binding.tbIndexBooking)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLastLocation()

        setHasOptionsMenu(true)
        setupDoctorList()

        binding.svSearchDoctor.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(p0: String?): Boolean {
                // make a server call
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val intent = Intent(requireActivity(), SearchDoctorActivity::class.java)
                intent.putExtra("query", p0)
                startActivity(intent)
                //clear the input
                binding.svSearchDoctor.setQuery("", false)
                //clear focus and keyboard
                binding.svSearchDoctor.clearFocus()
                return false
            }
        })
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation
                    .addOnCompleteListener {
                        val location: Location? = it.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            Toast.makeText(
                                context,
                                "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Please turn on your location...", Toast.LENGTH_LONG).show()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
            }
        } else {
            // request for permissions if not available
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.create().apply {
            interval = 5
            fastestInterval = 0
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location: Location = locationResult.lastLocation
            Toast.makeText(
                context,
                "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkPermissions(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            getLastLocation()
        }
    }

    private fun setupDoctorList() {
        val adapter = context?.let { DoctorRecyclerViewAdapter(it) }
        binding.rvDoctor.setHasFixedSize(true)
        binding.rvDoctor.adapter = adapter

        adapter?.onClickListener = {
            startActivity(Intent(requireActivity(), ProfilDoctorActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.index_doctor_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_history) {
            startActivity(Intent(activity, ListBookingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}