package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.data.database.ServiceRequestManager
import com.example.myapplication.data.model.ServiceRequest
import com.example.myapplication.data.model.ServiceType
import com.example.myapplication.data.communication.InterAppCommunication
import com.example.myapplication.databinding.ActivityServiceRequestBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ServiceRequestActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityServiceRequestBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null
    private var serviceType: ServiceType = ServiceType.TOWING
    
    // Mock vehicles data
    private val mockVehicles = listOf(
        "Toyota Camry - AB123CD",
        "BMW X5 - EF456GH", 
        "Nissan Qashqai - IJ789KL"
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Get service type from intent
        serviceType = ServiceType.valueOf(
            intent.getStringExtra("SERVICE_TYPE") ?: ServiceType.TOWING.name
        )
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        setupUI()
        getCurrentLocation()
    }
    
    private fun setupUI() {
        // Set service info based on type
        when (serviceType) {
            ServiceType.TOWING -> {
                binding.tvServiceTitle.text = "ბუქსირების სერვისი"
                binding.tvServiceDescription.text = "თქვენი მანქანა უსაფრთხოდ გადაიყვანება სასურველ ადგილზე"
                binding.tvEstimatedTime.text = "20-40 წუთი"
                binding.tvEstimatedPrice.text = "50-80 ლარი"
            }
            ServiceType.EVACUATION -> {
                binding.tvServiceTitle.text = "ევაკუატორის სერვისი"
                binding.tvServiceDescription.text = "მანქანის სრული ტრანსპორტირება სპეციალური ტექნიკით"
                binding.tvEstimatedTime.text = "30-60 წუთი"
                binding.tvEstimatedPrice.text = "80-150 ლარი"
            }
            ServiceType.BATTERY_JUMP -> {
                binding.tvServiceTitle.text = "აკუმულატორის დახმარება"
                binding.tvServiceDescription.text = "აკუმულატორის გამუხტვა და ძრავის ჩართვა"
                binding.tvEstimatedTime.text = "15-25 წუთი"
                binding.tvEstimatedPrice.text = "30-50 ლარი"
            }
            ServiceType.FUEL_DELIVERY -> {
                binding.tvServiceTitle.text = "საწვავის მიწოდება"
                binding.tvServiceDescription.text = "საწვავის მიტანა თქვენი მდებარეობის მიხედვით"
                binding.tvEstimatedTime.text = "20-35 წუთი"
                binding.tvEstimatedPrice.text = "40-60 ლარი"
            }
            ServiceType.TIRE_CHANGE -> {
                binding.tvServiceTitle.text = "გუმის შეცვლა"
                binding.tvServiceDescription.text = "დაზიანებული გუმის შეცვლა სარეზერვოთი"
                binding.tvEstimatedTime.text = "15-30 წუთი"
                binding.tvEstimatedPrice.text = "25-40 ლარი"
            }
            ServiceType.LOCKOUT_SERVICE -> {
                binding.tvServiceTitle.text = "ჩაკეტვისას დახმარება"
                binding.tvServiceDescription.text = "მანქანაში გასაღების დატოვებისას კარების გახსნა"
                binding.tvEstimatedTime.text = "10-20 წუთი"
                binding.tvEstimatedPrice.text = "40-70 ლარი"
            }
            else -> {
                binding.tvServiceTitle.text = "სერვისი"
                binding.tvServiceDescription.text = "გადაუდებელი დახმარება"
                binding.tvEstimatedTime.text = "15-30 წუთი"
                binding.tvEstimatedPrice.text = "30-50 ლარი"
            }
        }
        
        // Setup vehicle spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mockVehicles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerVehicles.adapter = adapter
        
        // Setup click listeners
        binding.btnAddVehicle.setOnClickListener {
            Toast.makeText(this, "მანქანის დამატება - მალე დაემატება", Toast.LENGTH_SHORT).show()
        }
        
        binding.btnRequestService.setOnClickListener {
            requestService()
        }
        
        binding.btnCancel.setOnClickListener {
            finish()
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
                        binding.tvCurrentLocation.text = 
                            "Lat: ${String.format("%.6f", location.latitude)}, " +
                            "Lng: ${String.format("%.6f", location.longitude)}\n" +
                            "მდებარეობა განსაზღვრულია ✓"
                    } else {
                        binding.tvCurrentLocation.text = "მდებარეობის განსაზღვრა ვერ მოხერხდა"
                    }
                }
                .addOnFailureListener {
                    binding.tvCurrentLocation.text = "მდებარეობის შეცდომა"
                }
        }
    }
    
    private fun requestService() {
        val selectedVehicle = binding.spinnerVehicles.selectedItem?.toString() ?: ""
        val locationDetails = binding.etLocationDetails.text.toString()
        val problemDescription = binding.etProblemDescription.text.toString()
        
        if (selectedVehicle.isEmpty()) {
            Toast.makeText(this, "გთხოვთ აირჩიოთ მანქანა", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (problemDescription.isBlank()) {
            Toast.makeText(this, "გთხოვთ აღწერეთ პრობლემა", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Get user info from SharedPreferences
        val sharedPrefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val clientPhone = sharedPrefs.getString("user_phone", "") ?: ""
        val clientName = "კლიენტი $clientPhone" // You can expand this with more user data
        
        // Create service request
        val serviceRequest = ServiceRequest(
            clientName = clientName,
            clientPhone = clientPhone,
            vehicleId = selectedVehicle,
            serviceType = serviceType.displayNameGeo,
            latitude = currentLocation?.latitude ?: 0.0,
            longitude = currentLocation?.longitude ?: 0.0,
            address = locationDetails.ifEmpty { binding.tvCurrentLocation.text.toString() },
            description = problemDescription,
            status = "PENDING"
        )
        
        // Save request to local database
        val requestManager = ServiceRequestManager.getInstance(this)
        val requestId = requestManager.saveServiceRequest(serviceRequest)
        
        // Send request to Provider App through InterAppCommunication
        val communication = InterAppCommunication.getInstance(this)
        val success = communication.sendServiceRequest(serviceRequest)
        
        if (success) {
            Toast.makeText(this, "შეკვეთა გაიგზავნა Provider App-ში!\nშეკვეთის ID: $requestId", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "შეკვეთა შენახულია ლოკალურად\nშეკვეთის ID: $requestId", Toast.LENGTH_LONG).show()
        }
        
        // Also make a phone call as backup
        val phoneNumber = getServicePhoneNumber()
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse(phoneNumber)
        }
        startActivity(intent)
        
        finish()
    }
    
    private fun getServicePhoneNumber(): String {
        return when (serviceType) {
            ServiceType.TOWING -> "tel:+995599123456"
            ServiceType.EVACUATION -> "tel:+995599654321"
            ServiceType.BATTERY_JUMP -> "tel:+995599111222"
            ServiceType.FUEL_DELIVERY -> "tel:+995599333444"
            ServiceType.TIRE_CHANGE -> "tel:+995599555666"
            ServiceType.LOCKOUT_SERVICE -> "tel:+995599777888"
            else -> "tel:+995599000000"
        }
    }
}