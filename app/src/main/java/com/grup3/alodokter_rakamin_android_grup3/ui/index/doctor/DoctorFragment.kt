package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.DoctorRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentDoctorBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.ListBookingActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail.ProfilDoctorActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DoctorFragment : BaseFragment<FragmentDoctorBinding>() {

    private lateinit var requestLauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location
    private lateinit var locationRequest: LocationRequest

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDoctorBinding = FragmentDoctorBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as IndexActivity).setSupportActionBar(binding.tbIndexBooking)

        setupPermissionReqLauncher()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getLocation()

        setHasOptionsMenu(true)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fusedLocationClient.lastLocation.addOnCompleteListener {
                    val location: Location? = it.result
                    if (location == null) {
                        Log.d("TAG", "onLocationResult: null dipanggil")
                        requestNewLocationData()
                    } else {
                        Toast.makeText(
                            context,
                            "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                            Toast.LENGTH_SHORT
                        ).show()
                        setupDoctorList()
                    }
                }
            } else {
                showEnableLocationSetting()
            }
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun setupPermissionReqLauncher() {
        requestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                // Location permission granted
                getLocation()
            } else {
                // Location permission rejected
                requestPermissions()
            }
        }
    }

    private fun requestPermissions() {
        requestLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun showEnableLocationSetting() {
        activity?.let {
            locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

            val task = LocationServices.getSettingsClient(it)
                .checkLocationSettings(builder.build())

            task.addOnSuccessListener { response ->
                val states = response.locationSettingsStates
                if (states?.isLocationPresent == true) {
                    getLocation()
                }
            }
            task.addOnFailureListener { e ->
                if (e is ResolvableApiException) {
                    try {
                        startIntentSenderForResult(
                            e.resolution.intentSender,
                            LOCATION_SETTING_REQUEST,
                            null, 0, 0, 0, null
                        )
                    } catch (sendEx: SendIntentException) {
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOCATION_SETTING_REQUEST -> when (resultCode) {
                Activity.RESULT_OK -> {
                    getLocation()
                }
                Activity.RESULT_CANCELED -> {
                    showEnableLocationSetting()
                    Toast.makeText(context, "Location Service not Enabled", Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {}
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        Log.d("TAG", "onLocationResult: dipanggil req")
        val locationRequest = LocationRequest.create().apply {
            interval = 50000
            fastestInterval = 50000
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        setupDoctorList()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            Log.d("TAG", "onLocationResult: dipanggil call")
            location = locationResult.lastLocation
            Toast.makeText(
                context,
                "Latitude: ${location.latitude}, Longitude: ${location.longitude}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
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

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    companion object {
        const val LOCATION_SETTING_REQUEST = 1010
    }

}