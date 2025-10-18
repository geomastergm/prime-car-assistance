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
import com.google.firebase.database.*
import com.example.myapplication.data.model.ServiceRequest
import com.google.firebase.auth.FirebaseAuth
import com.example.myapplication.adapter.ServiceRequestAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var database: FirebaseDatabase
    private lateinit var serviceRequestsRef: DatabaseReference
    private var currentLocation: Location? = null
    private lateinit var serviceRequestAdapter: ServiceRequestAdapter
    
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
        
        // Firebase ინიციალიზაცია - CORRECT Europe West 1 URL
        database = FirebaseDatabase.getInstance("https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app/")
        serviceRequestsRef = database.getReference("service_requests")
        
        // Debug: Print database URL
        android.util.Log.d("Firebase", "Provider: Database URL: ${database.app.options.databaseUrl}")
        
        // RecyclerView სეტაპი
        setupRecyclerView()
        
        // Debug: print Firebase Auth status
        android.util.Log.d("Firebase", "Provider: Firebase Auth User: ${FirebaseAuth.getInstance().currentUser}")
        
        // სერვის მოთხოვნების რეალურ დროში მონიტორინგი
        android.util.Log.d("Firebase", "Provider: Setting up ValueEventListener")
        serviceRequestsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                android.util.Log.d("Firebase", "Provider: onDataChange called, children count: ${snapshot.childrenCount}")
                val requestsList = mutableListOf<ServiceRequest>()
                for (childSnapshot in snapshot.children) {
                    val request = childSnapshot.getValue(ServiceRequest::class.java)
                    android.util.Log.d("Firebase", "Provider: Found request: ${request?.id} - ${request?.serviceType}")
                    request?.let { requestsList.add(it) }
                }
                android.util.Log.d("Firebase", "Provider: Total requests: ${requestsList.size}")
                updateRequestsUI(requestsList)
            }
            
            override fun onCancelled(error: DatabaseError) {
                // ერორის დამუშავება
                android.util.Log.e("Firebase", "Provider: onCancelled - ${error.message}")
                binding.tvLocationStatus.text = "Firebase ერორი: ${error.message}"
            }
        })
        
        // ავტორიზაციის შემოწმება
        checkAuthentication()
        
        setupUI()
        checkLocationPermission()
    }
    
    private fun checkAuthentication() {
        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        val savedUsername = sharedPreferences.getString("username", "") ?: ""
        
        if (savedUsername.isEmpty()) {
            // თუ არ არის ავტორიზებული, LoginActivity-ზე გადაყვანა
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
        
        // Admin panel-ის visibility თუ არის azrikunikatuno
        if (savedUsername == "azrikunikatuno") {
            binding.tvAdminSection.visibility = android.view.View.VISIBLE
            binding.adminSectionContainer.visibility = android.view.View.VISIBLE
            
            // Admin access logging
            logAdminAccess(savedUsername)
        } else {
            binding.tvAdminSection.visibility = android.view.View.GONE
            binding.adminSectionContainer.visibility = android.view.View.GONE
        }
    }
    
    private fun logAdminAccess(username: String) {
        val adminLogsRef = database.getReference("admin_logs")
        val logEntry = mapOf(
            "username" to username,
            "action" to "LOGIN",
            "timestamp" to System.currentTimeMillis(),
            "device_info" to "${android.os.Build.MODEL} (${android.os.Build.VERSION.RELEASE})"
        )
        
        adminLogsRef.push().setValue(logEntry)
            .addOnSuccessListener {
                android.util.Log.d("Firebase", "Admin access logged successfully")
            }
            .addOnFailureListener { exception ->
                android.util.Log.e("Firebase", "Failed to log admin access: ${exception.message}")
            }
    }
    
    private fun authenticateFirebase() {
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            auth.signInAnonymously()
                .addOnSuccessListener {
                    android.util.Log.d("Firebase", "Provider anonymous auth successful")
                }
                .addOnFailureListener { exception ->
                    android.util.Log.e("Firebase", "Provider anonymous auth failed: ${exception.message}")
                }
        }
    }
    
    private fun setupUI() {
        // SwipeRefreshLayout setup - მექანიკური განახლება
        binding.swipeRefresh.setOnRefreshListener {
            android.util.Log.d("Firebase", "Provider: Manual refresh triggered")
            refreshFirebaseData()
        }
        
        // Admin Panel ღილაკები (მხოლოდ azrikunikatuno-სთვის)
        binding.btnRegistrationRequests.setOnClickListener {
            // სარეგისტრაციო განცხადებების Activity-ზე გადასვლა
            startActivity(Intent(this, RegistrationRequestsActivity::class.java))
        }
        
        binding.btnApproveProviders.setOnClickListener {
            // პროვაიდერების დადასტურების Activity-ზე გადასვლა  
            Toast.makeText(this, "პროვაიდერების დადასტურების გვერდი", Toast.LENGTH_SHORT).show()
            // TODO: Create ApproveProvidersActivity
        }
        
        // Logout ღილაკი
        binding.btnLogout.setOnClickListener {
            logout()
        }
        
        // Settings ღილაკი
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
    
    private fun refreshFirebaseData() {
        android.util.Log.d("Firebase", "Provider: refreshFirebaseData called")
        
        // Manual Firebase data refresh with timeout
        serviceRequestsRef.get().addOnSuccessListener { snapshot ->
            android.util.Log.d("Firebase", "Provider: Manual refresh - got ${snapshot.childrenCount} items")
            
            val requestsList = mutableListOf<ServiceRequest>()
            for (childSnapshot in snapshot.children) {
                val request = childSnapshot.getValue(ServiceRequest::class.java)
                request?.let { 
                    requestsList.add(it)
                    android.util.Log.d("Firebase", "Provider: Manual refresh - found request: ${it.id}")
                }
            }
            
            // განახლების ანიმაციის შეწყვეტა
            binding.swipeRefresh.isRefreshing = false
            updateRequestsUI(requestsList)
            Toast.makeText(this, "განახლდა! მოთხოვნები: ${requestsList.size}", Toast.LENGTH_SHORT).show()
            
        }.addOnFailureListener { error ->
            android.util.Log.e("Firebase", "Provider: Manual refresh failed: ${error.message}")
            binding.swipeRefresh.isRefreshing = false
            Toast.makeText(this, "განახლების შეცდომა: ${error.message}", Toast.LENGTH_SHORT).show()
        }
        
        // ავტომატური timeout 10 წამის შემდეგ
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (binding.swipeRefresh.isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
                Toast.makeText(this, "განახლება ვერ დასრულდა დროულად", Toast.LENGTH_SHORT).show()
                android.util.Log.w("Firebase", "Provider: Manual refresh timeout after 10 seconds")
            }
        }, 10000) // 10 წამი
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
    
    private fun setupRecyclerView() {
        serviceRequestAdapter = ServiceRequestAdapter(
            requests = emptyList(),
            onAcceptClick = { request -> acceptRequest(request) },
            onRejectClick = { request -> rejectRequest(request) },
            onItemClick = { request -> openMapActivity(request) }
        )
        
        binding.rvServiceRequests.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = serviceRequestAdapter
        }
    }
    
    private fun openMapActivity(request: ServiceRequest) {
        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("service_request", request)
        startActivity(intent)
    }
    
    private fun acceptRequest(request: ServiceRequest) {
        // მოთხოვნის მიღება - Firebase-ში სტატუსის განახლება
        val updatedRequest = request.copy(status = "ACCEPTED")
        serviceRequestsRef.child(request.id).setValue(updatedRequest)
        Toast.makeText(this, "მოთხოვნა მიღებულია", Toast.LENGTH_SHORT).show()
    }
    
    private fun rejectRequest(request: ServiceRequest) {
        // მოთხოვნის უარყოფა
        val updatedRequest = request.copy(status = "REJECTED")
        serviceRequestsRef.child(request.id).setValue(updatedRequest)
        Toast.makeText(this, "მოთხოვნა უარყოფილია", Toast.LENGTH_SHORT).show()
    }
    
    private fun updateRequestsUI(requestsList: List<ServiceRequest>) {
        android.util.Log.d("Firebase", "Provider updateRequestsUI: Called with ${requestsList.size} requests")
        
        // Firebase-დან მიღებული მოთხოვნების საფუძველზე UI განახლება
        if (requestsList.isNotEmpty()) {
            android.util.Log.d("Firebase", "Provider updateRequestsUI: Updating RecyclerView with requests")
            
            // RecyclerView-ის განახლება
            serviceRequestAdapter.updateRequests(requestsList)
            binding.tvNoRequests.visibility = android.view.View.GONE
            binding.rvServiceRequests.visibility = android.view.View.VISIBLE
            
            // მდებარეობის სტატუსი
            val activeRequests = requestsList.filter { it.status == "PENDING" }
            val statusText = if (activeRequests.isNotEmpty()) {
                "აქტიური მოთხოვნები: ${activeRequests.size}"
            } else {
                "ახალი მოთხოვნების მოლოდინში..."
            }
            binding.tvLocationStatus.text = statusText
            
            android.util.Log.d("Firebase", "Provider updateRequestsUI: Status updated to: $statusText")
        } else {
            android.util.Log.d("Firebase", "Provider updateRequestsUI: No requests, showing empty state")
            
            // არ არის მოთხოვნები
            binding.tvNoRequests.visibility = android.view.View.VISIBLE
            binding.rvServiceRequests.visibility = android.view.View.GONE
            binding.tvLocationStatus.text = "მოთხოვნების მოლოდინში..."
        }
        
        android.util.Log.d("Firebase", "Provider updateRequestsUI: UI update completed")
    }
    
    private fun logout() {
        // Clear shared preferences
        val sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
        
        // Navigate back to login
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
        
        Toast.makeText(this, "წარმატებით გახვედით", Toast.LENGTH_SHORT).show()
    }
}