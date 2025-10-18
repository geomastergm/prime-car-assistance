package com.example.myapplication

import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.model.ServiceRequest
import com.example.myapplication.databinding.ActivityMapBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    
    private lateinit var binding: ActivityMapBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var serviceRequest: ServiceRequest? = null
    private var providerLocation: Location? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // მოთხოვნის მონაცემების მიღება
        serviceRequest = intent.getSerializableExtra("service_request") as? ServiceRequest
        
        if (serviceRequest == null) {
            Toast.makeText(this, "შეცდომა: მოთხოვნის მონაცემები არ მოიძებნა", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        // Map Fragment-ის ინიციალიზაცია
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
        setupUI()
    }
    
    private fun setupUI() {
        serviceRequest?.let { request ->
            binding.tvServiceType.text = request.serviceType
            binding.tvClientLocation.text = "${request.latitude}, ${request.longitude}"
            binding.tvPrice.text = "${request.price} ₾"
            binding.tvDescription.text = request.description
            
            // სტატუსის ფერი
            when (request.status) {
                "PENDING" -> binding.tvStatus.setBackgroundColor(getColor(android.R.color.holo_orange_light))
                "ACCEPTED" -> binding.tvStatus.setBackgroundColor(getColor(android.R.color.holo_green_light))
                "REJECTED" -> binding.tvStatus.setBackgroundColor(getColor(android.R.color.holo_red_light))
            }
            binding.tvStatus.text = request.status
        }
        
        // ღილაკების მოვლენები
        binding.btnAccept.setOnClickListener {
            // მოთხოვნის მიღება
            Toast.makeText(this, "მოთხოვნა მიღებულია", Toast.LENGTH_SHORT).show()
            finish()
        }
        
        binding.btnReject.setOnClickListener {
            // მოთხოვნის უარყოფა
            Toast.makeText(this, "მოთხოვნა უარყოფილია", Toast.LENGTH_SHORT).show()
            finish()
        }
        
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
    
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        
        serviceRequest?.let { request ->
            val clientLocation = LatLng(request.latitude, request.longitude)
            
            // კლიენტის მარკერი
            googleMap.addMarker(
                MarkerOptions()
                    .position(clientLocation)
                    .title("კლიენტის ლოკაცია")
                    .snippet("${request.serviceType} - ${request.price} ₾")
            )
            
            // მიმდინარე ლოკაციის მიღება
            getCurrentLocation { providerLoc ->
                providerLocation = providerLoc
                val providerLatLng = LatLng(providerLoc.latitude, providerLoc.longitude)
                
                // პროვაიდერის მარკერი
                googleMap.addMarker(
                    MarkerOptions()
                        .position(providerLatLng)
                        .title("თქვენი ლოკაცია")
                        .snippet("პროვაიდერი")
                )
                
                // მანძილის გაანგარიშება
                val distance = calculateDistance(
                    providerLoc.latitude, providerLoc.longitude,
                    request.latitude, request.longitude
                )
                
                binding.tvDistance.text = "მანძილი: ${String.format("%.2f", distance)} კმ"
                
                // კამერის პოზიცია ორივე მარკერზე
                val bounds = com.google.android.gms.maps.model.LatLngBounds.builder()
                    .include(clientLocation)
                    .include(providerLatLng)
                    .build()
                
                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150))
            }
        }
    }
    
    private fun getCurrentLocation(callback: (Location) -> Unit) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    callback(location)
                } else {
                    // დეფოლტ ლოკაცია (თბილისი)
                    val defaultLocation = Location("").apply {
                        latitude = 41.7151
                        longitude = 44.8271
                    }
                    callback(defaultLocation)
                    binding.tvDistance.text = "მანძილი: ვერ გაიანგარიშა"
                }
            }.addOnFailureListener {
                // დეფოლტ ლოკაცია (თბილისი)
                val defaultLocation = Location("").apply {
                    latitude = 41.7151
                    longitude = 44.8271
                }
                callback(defaultLocation)
                binding.tvDistance.text = "მანძილი: ვერ გაიანგარიშა"
            }
        } catch (e: SecurityException) {
            // დეფოლტ ლოკაცია (თბილისი)
            val defaultLocation = Location("").apply {
                latitude = 41.7151
                longitude = 44.8271
            }
            callback(defaultLocation)
            binding.tvDistance.text = "მანძილი: ვერ გაიანგარიშა"
        }
    }
    
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371.0 // კმ
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        
        return earthRadius * c
    }
}