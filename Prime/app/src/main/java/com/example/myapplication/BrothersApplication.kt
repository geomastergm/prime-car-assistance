package com.example.myapplication

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BrothersApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Enable Firebase Realtime Database offline persistence - CORRECT Europe West 1 URL
        try {
            val database = FirebaseDatabase.getInstance("https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app/")
            database.setPersistenceEnabled(true)
            android.util.Log.d("Firebase", "Client: Persistence enabled successfully")
        } catch (e: Exception) {
            android.util.Log.e("Firebase", "Persistence already enabled: ${e.message}")
        }
        
        // Initialize Firebase Auth and sign in anonymously for client access
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                android.util.Log.d("Firebase", "Client: Anonymous authentication successful")
            } else {
                android.util.Log.e("Firebase", "Client: Anonymous authentication failed: ${task.exception?.message}")
            }
        }
    }
}