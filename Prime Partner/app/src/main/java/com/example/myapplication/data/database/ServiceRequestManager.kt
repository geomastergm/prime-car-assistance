package com.example.myapplication.data.database

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.data.model.ServiceRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString

class ServiceRequestManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = 
        context.getSharedPreferences("ServiceRequests", Context.MODE_PRIVATE)
    private val json = Json { ignoreUnknownKeys = true }
    
    fun saveServiceRequest(request: ServiceRequest): String {
        val requestId = generateRequestId()
        val requestWithId = request.copy(id = requestId)
        
        val currentRequests = getAllRequests().toMutableList()
        currentRequests.add(requestWithId)
        
        saveRequests(currentRequests)
        
        // Notify Service Provider App
        notifyServiceProviders(requestWithId)
        
        return requestId
    }
    
    fun getAllRequests(): List<ServiceRequest> {
        val jsonString = sharedPreferences.getString("requests", "[]") ?: "[]"
        return try {
            json.decodeFromString<List<ServiceRequest>>(jsonString)
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun getPendingRequests(): List<ServiceRequest> {
        return getAllRequests().filter { it.status == "PENDING" }
    }
    
    fun updateRequestStatus(requestId: String, status: String, providerId: String? = null): Boolean {
        val requests = getAllRequests().toMutableList()
        val index = requests.indexOfFirst { it.id == requestId }
        
        if (index != -1) {
            requests[index] = requests[index].copy(
                status = status,
                assignedProviderId = providerId,
                updatedAt = System.currentTimeMillis()
            )
            saveRequests(requests)
            return true
        }
        return false
    }
    
    private fun saveRequests(requests: List<ServiceRequest>) {
        val jsonString = json.encodeToString(requests)
        sharedPreferences.edit().putString("requests", jsonString).apply()
    }
    
    private fun generateRequestId(): String {
        return "REQ_${System.currentTimeMillis()}_${(1000..9999).random()}"
    }
    
    private fun notifyServiceProviders(request: ServiceRequest) {
        // Save to provider notification queue
        val providerPrefs = sharedPreferences.edit()
        providerPrefs.putString("new_request_${request.id}", json.encodeToString(request))
        providerPrefs.putLong("last_request_time", System.currentTimeMillis())
        providerPrefs.apply()
    }
    
    companion object {
        @Volatile
        private var INSTANCE: ServiceRequestManager? = null
        
        fun getInstance(context: Context): ServiceRequestManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ServiceRequestManager(context).also { INSTANCE = it }
            }
        }
    }
}