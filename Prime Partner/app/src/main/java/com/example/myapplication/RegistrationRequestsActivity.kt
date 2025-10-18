package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.RegistrationRequestsAdapter
import com.example.myapplication.data.model.ProviderRegistration
import com.example.myapplication.databinding.ActivityRegistrationRequestsBinding
import com.google.firebase.database.*

class RegistrationRequestsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityRegistrationRequestsBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var registrationsRef: DatabaseReference
    private lateinit var adapter: RegistrationRequestsAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupFirebase()
        setupRecyclerView()
        loadRegistrations()
        
        binding.btnBack.setOnClickListener { finish() }
    }
    
    private fun setupFirebase() {
        database = FirebaseDatabase.getInstance("https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app/")
        registrationsRef = database.getReference("provider_registrations")
    }
    
    private fun setupRecyclerView() {
        adapter = RegistrationRequestsAdapter(
            registrations = emptyList(),
            onApprove = { registration -> approveRegistration(registration) },
            onReject = { registration -> rejectRegistration(registration) }
        )
        
        binding.rvRegistrations.apply {
            layoutManager = LinearLayoutManager(this@RegistrationRequestsActivity)
            adapter = this@RegistrationRequestsActivity.adapter
        }
    }
    
    private fun loadRegistrations() {
        binding.progressBar.visibility = View.VISIBLE
        
        registrationsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val registrations = mutableListOf<ProviderRegistration>()
                for (childSnapshot in snapshot.children) {
                    val registration = childSnapshot.getValue(ProviderRegistration::class.java)
                    registration?.let { registrations.add(it) }
                }
                
                updateUI(registrations)
                binding.progressBar.visibility = View.GONE
            }
            
            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@RegistrationRequestsActivity, "Firebase ერორი: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    private fun updateUI(registrations: List<ProviderRegistration>) {
        if (registrations.isNotEmpty()) {
            binding.tvEmpty.visibility = View.GONE
            binding.rvRegistrations.visibility = View.VISIBLE
            adapter.updateRegistrations(registrations)
        } else {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvRegistrations.visibility = View.GONE
        }
    }
    
    private fun approveRegistration(registration: ProviderRegistration) {
        val updatedRegistration = registration.copy(
            status = "APPROVED",
            reviewedAt = System.currentTimeMillis(),
            reviewedBy = "azrikunikatuno"
        )
        
        registrationsRef.child(registration.id).setValue(updatedRegistration)
            .addOnSuccessListener {
                Toast.makeText(this, "განცხადება დამტკიცდა", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "ერორი: ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
    
    private fun rejectRegistration(registration: ProviderRegistration) {
        val updatedRegistration = registration.copy(
            status = "REJECTED",
            reviewedAt = System.currentTimeMillis(),
            reviewedBy = "azrikunikatuno",
            notes = "ადმინისტრატორის მიერ უარყოფილია"
        )
        
        registrationsRef.child(registration.id).setValue(updatedRegistration)
            .addOnSuccessListener {
                Toast.makeText(this, "განცხადება უარყოფილია", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "ერორი: ${error.message}", Toast.LENGTH_SHORT).show()
            }
    }
}