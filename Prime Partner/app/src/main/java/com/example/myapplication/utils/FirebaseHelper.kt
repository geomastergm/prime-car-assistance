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
     * Get reference to providers node
     */
    fun getProvidersRef(): DatabaseReference {
        return firebaseDatabase.getReference("providers")
    }
    
    /**
     * Get reference to provider_registrations node
     */
    fun getProviderRegistrationsRef(): DatabaseReference {
        return firebaseDatabase.getReference("provider_registrations")
    }
    
    /**
     * Get reference to payments node
     */
    fun getPaymentsRef(): DatabaseReference {
        return firebaseDatabase.getReference("payments")
    }
    
    /**
     * Get reference to provider_wallets node
     */
    fun getProviderWalletsRef(): DatabaseReference {
        return firebaseDatabase.getReference("provider_wallets")
    }
    
    /**
     * Get reference to withdrawal_requests node
     */
    fun getWithdrawalRequestsRef(): DatabaseReference {
        return firebaseDatabase.getReference("withdrawal_requests")
    }
    
    /**
     * Get reference to bank_accounts node
     */
    fun getBankAccountsRef(): DatabaseReference {
        return firebaseDatabase.getReference("bank_accounts")
    }
    
    /**
     * Get reference for specific provider
     */
    fun getProviderRef(providerId: String): DatabaseReference {
        return getProvidersRef().child(providerId)
    }
    
    /**
     * Get reference for specific service request
     */
    fun getServiceRequestRef(requestId: String): DatabaseReference {
        return getServiceRequestsRef().child(requestId)
    }
}
