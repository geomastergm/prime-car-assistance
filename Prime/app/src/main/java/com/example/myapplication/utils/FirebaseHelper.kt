package com.example.myapplication.utils

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

/**
 * Firebase Helper - ცენტრალიზებული Firebase Database წვდომა
 */
object FirebaseHelper {
    
    // Firebase Realtime Database URL - Europe West 1
    private const val DATABASE_URL = "https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app/"
    
    // Database instance - lazy initialization
    private val firebaseDatabase: FirebaseDatabase by lazy {
        FirebaseDatabase.getInstance(DATABASE_URL).apply {
            // Offline persistence
            setPersistenceEnabled(true)
        }
    }
    
    /**
     * Get Firebase Database instance
     */
    fun getDatabase(): FirebaseDatabase = firebaseDatabase
    
    /**
     * Get reference to service_requests node
     */
    fun getServiceRequestsRef(): DatabaseReference {
        return firebaseDatabase.getReference("service_requests")
    }
    
    /**
     * Get reference to users node
     */
    fun getUsersRef(): DatabaseReference {
        return firebaseDatabase.getReference("users")
    }
    
    /**
     * Get reference to vehicles node
     */
    fun getVehiclesRef(): DatabaseReference {
        return firebaseDatabase.getReference("vehicles")
    }
    
    /**
     * Get reference to providers node
     */
    fun getProvidersRef(): DatabaseReference {
        return firebaseDatabase.getReference("providers")
    }
    
    /**
     * Get reference to payments node
     */
    fun getPaymentsRef(): DatabaseReference {
        return firebaseDatabase.getReference("payments")
    }
    
    /**
     * Get reference for specific user
     */
    fun getUserRef(userId: String): DatabaseReference {
        return getUsersRef().child(userId)
    }
    
    /**
     * Get reference for specific service request
     */
    fun getServiceRequestRef(requestId: String): DatabaseReference {
        return getServiceRequestsRef().child(requestId)
    }
}
