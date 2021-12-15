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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.DoctorRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentDoctorBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.article.search.SearchArticleActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.DetailBookingActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexSharedViewModel
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.bookhistory.ListBookingActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail.ProfilDoctorActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.search.SearchDoctorActivity
import java.util.*

import com.grup3.alodokter_rakamin_android_grup3.ui.profile.ProfileActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInActivity
import dagger.hilt.android.AndroidEntryPoint

const val PERMISSION_ID = 1010

@AndroidEntryPoint
class DoctorFragment : BaseFragment<FragmentDoctorBinding>() {

    private lateinit var requestLauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var location: Location
    private lateinit var locationRequest: LocationRequest
    private val viewModel: IndexSharedViewModel by viewModels()

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
        when (item.itemId) {
            R.id.menu_history -> {
                if (viewModel.getUserLoginStatus()) {
                    startActivity(Intent(activity, ListBookingActivity::class.java))
                } else {
                    showLoginDialog()
                }
            }
            R.id.menu_profile -> {
                if (viewModel.getUserLoginStatus()) {
                    startActivity(Intent(activity, ProfileActivity::class.java))
                } else {
                    showLoginDialog()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun showLoginDialog() {
        val view = View.inflate(requireActivity(), R.layout.custom_alert_dialog_login, null)

        view.findViewById<TextView>(R.id.tv_alert_title).text =
            resources.getString(R.string.title_alert_login_at_profile)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        val dialog = builder.setCancelable(false).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.findViewById<TextView>(R.id.btn_redirect_masuk).setOnClickListener {
            startActivity(Intent(requireActivity(), SignInActivity::class.java))
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.btn_nanti).setOnClickListener {
            dialog.dismiss()
        }
    }

    companion object {
        const val LOCATION_SETTING_REQUEST = 1010
    }

}