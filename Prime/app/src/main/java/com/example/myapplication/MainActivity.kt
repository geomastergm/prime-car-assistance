package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.data.model.ServiceType
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    
    // Permission launcher
    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                getCurrentLocation()
            }
            else -> {
                binding.tvLocationStatus.text = "მდებარეობის ნებართვა საჭიროა"
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        setupUI()
        checkLocationPermission()
    }
    
    private fun setupUI() {
        // Set up service click listeners
        binding.cvTowing.setOnClickListener { 
            showServiceDialog(ServiceType.TOWING)
        }
        
        binding.cvEvacuation.setOnClickListener { 
            showServiceDialog(ServiceType.EVACUATION)
        }
        
        binding.cvBattery.setOnClickListener { 
            showServiceDialog(ServiceType.BATTERY_JUMP)
        }
        
        binding.cvFuel.setOnClickListener { 
            showServiceDialog(ServiceType.FUEL_DELIVERY)
        }
        
        binding.cvTire.setOnClickListener { 
            showServiceDialog(ServiceType.TIRE_CHANGE)
        }
        
        binding.cvLockout.setOnClickListener { 
            showServiceDialog(ServiceType.LOCKOUT_SERVICE)
        }
        
        // Bottom buttons
        binding.btnMyVehicles.setOnClickListener {
            val intent = Intent(this, MyVehiclesActivity::class.java)
            startActivity(intent)
        }
        
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                getCurrentLocation()
            }
            else -> {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }
    
    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        currentLocation = location
                        binding.tvLocationStatus.text = "მდებარეობა განსაზღვრულია ✓"
                    } else {
                        binding.tvLocationStatus.text = "მდებარეობის განსაზღვრა ვერ მოხერხდა"
                    }
                }
                .addOnFailureListener {
                    binding.tvLocationStatus.text = "მდებარეობის შეცდომა"
                }
        }
    }
    
    private fun showServiceDialog(serviceType: ServiceType) {
        // Open detailed service request activity
        val intent = Intent(this, ServiceRequestActivity::class.java).apply {
            putExtra("SERVICE_TYPE", serviceType.name)
        }
        startActivity(intent)
    }
    
    private fun callEmergencyService(serviceType: ServiceType) {
        // Demo phone numbers for different services
        val phoneNumber = when (serviceType) {
            ServiceType.TOWING -> "tel:+995599123456"
            ServiceType.EVACUATION -> "tel:+995599654321"
            ServiceType.BATTERY_JUMP -> "tel:+995599111222"
            ServiceType.FUEL_DELIVERY -> "tel:+995599333444"
            ServiceType.TIRE_CHANGE -> "tel:+995599555666"
            ServiceType.LOCKOUT_SERVICE -> "tel:+995599777888"
            else -> "tel:+995599000000"
        }
        
        val intent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse(phoneNumber)
        }
        
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) 
            == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        } else {
            // If no call permission, open dialer instead
            val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse(phoneNumber)
            }
            startActivity(dialIntent)
        }
        
        Toast.makeText(this, "${serviceType.displayNameGeo} სერვისის გამოძახება...", Toast.LENGTH_SHORT).show()
    }
}