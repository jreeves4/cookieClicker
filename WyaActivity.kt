package com.example.cookieclicker

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class WyaActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val LOCATION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wya)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setupLocationCallback()  // Initialize location callback

        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            finish()
        }

        val checkLocationButton: Button = findViewById(R.id.buttonCheckLocation)
        checkLocationButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            } else {
                requestLocationUpdates()  // Request location updates directly
            }
        }
    }

    private fun createLocationRequest(): LocationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = 10000
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, Looper.getMainLooper())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdates()  // Also ensure location updates are requested when resuming the app
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun setupLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {  // Note: nullable removed
                for (location in locationResult.locations) {
                    if (location != null) {
                        checkIfLocationIsInMaryland(location)
                        break  // Stop updates if not needed anymore
                    }
                }
            }
        }
    }

    private fun checkIfLocationIsInMaryland(location: android.location.Location) {
        print("cheese " + location)
        print("burger " + location.latitude)
        val inMaryland = location.latitude >= 37.886 && location.latitude <= 39.723 &&
                location.longitude >= -79.487 && location.longitude <= -75.048
//        if (inMaryland) {
            Toast.makeText(this, "You are in Maryland! Herve teaches at the University in that state!", Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this, "You are not in Maryland. Herve does not teach in your state...", Toast.LENGTH_LONG).show()
//        }
        fusedLocationClient.removeLocationUpdates(locationCallback)  // Stop updates after check
    }
}