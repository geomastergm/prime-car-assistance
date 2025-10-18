package com.example.myapplication.data.communication

import android.content.Context
import com.example.myapplication.data.model.ServiceRequest
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * Communication bridge between Client App and Provider App
 * Uses shared storage to exchange service requests
 */
class InterAppCommunication private constructor(private val context: Context) {
    
    companion object {
        private const val SHARED_FOLDER = "emergency_requests"
        private const val REQUEST_FILE_PREFIX = "request_"
        private const val FILE_EXTENSION = ".json"
        
        @Volatile
        private var INSTANCE: InterAppCommunication? = null
        
        fun getInstance(context: Context): InterAppCommunication {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: InterAppCommunication(context.applicationContext).also { INSTANCE = it }
            }
        }
    }
    
    private val sharedDir = File(context.getExternalFilesDir(null), SHARED_FOLDER).apply {
        if (!exists()) mkdirs()
    }
    
    /**
     * Send service request to provider app
     */
    fun sendServiceRequest(serviceRequest: ServiceRequest): Boolean {
        return try {
            val requestId = generateRequestId()
            val fileName = "$REQUEST_FILE_PREFIX$requestId$FILE_EXTENSION"
            val file = File(sharedDir, fileName)
            
            val requestData = RequestData(
                id = requestId,
                serviceType = serviceRequest.serviceType,
                customerName = serviceRequest.clientName,
                customerPhone = serviceRequest.clientPhone,
                location = serviceRequest.address,
                vehicleInfo = "${serviceRequest.latitude},${serviceRequest.longitude}",
                timestamp = System.currentTimeMillis(),
                status = "pending"
            )
            
            val json = Json.encodeToString(requestData)
            file.writeText(json)
            
            // Also save to shared preferences for real-time updates
            saveToSharedPrefs(requestId, json)
            
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Get all pending requests (for provider app)
     */
    fun getPendingRequests(): List<RequestData> {
        return try {
            sharedDir.listFiles { _, name -> 
                name.startsWith(REQUEST_FILE_PREFIX) && name.endsWith(FILE_EXTENSION)
            }?.mapNotNull { file ->
                try {
                    val json = file.readText()
                    Json.decodeFromString<RequestData>(json)
                } catch (e: Exception) {
                    null
                }
            }?.filter { it.status == "pending" } ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
    
    /**
     * Update request status (for provider app)
     */
    fun updateRequestStatus(requestId: String, status: String, providerId: String? = null): Boolean {
        return try {
            val fileName = "$REQUEST_FILE_PREFIX$requestId$FILE_EXTENSION"
            val file = File(sharedDir, fileName)
            
            if (file.exists()) {
                val json = file.readText()
                val request = Json.decodeFromString<RequestData>(json)
                val updatedRequest = request.copy(
                    status = status,
                    providerId = providerId,
                    updatedAt = System.currentTimeMillis()
                )
                
                file.writeText(Json.encodeToString(updatedRequest))
                updateSharedPrefs(requestId, Json.encodeToString(updatedRequest))
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    /**
     * Check request status (for client app)
     */
    fun getRequestStatus(requestId: String): RequestData? {
        return try {
            val fileName = "$REQUEST_FILE_PREFIX$requestId$FILE_EXTENSION"
            val file = File(sharedDir, fileName)
            
            if (file.exists()) {
                val json = file.readText()
                Json.decodeFromString<RequestData>(json)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    private fun generateRequestId(): String {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        val random = (1000..9999).random()
        return "$timestamp$random"
    }
    
    private fun saveToSharedPrefs(requestId: String, json: String) {
        val prefs = context.getSharedPreferences("emergency_requests", Context.MODE_PRIVATE)
        prefs.edit().putString(requestId, json).apply()
    }
    
    private fun updateSharedPrefs(requestId: String, json: String) {
        saveToSharedPrefs(requestId, json)
    }
}

@Serializable
data class RequestData(
    val id: String,
    val serviceType: String,
    val customerName: String,
    val customerPhone: String,
    val location: String,
    val vehicleInfo: String,
    val timestamp: Long,
    val status: String,
    val providerId: String? = null,
    val updatedAt: Long? = null
)