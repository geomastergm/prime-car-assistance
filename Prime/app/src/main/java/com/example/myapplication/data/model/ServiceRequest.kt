package com.example.myapplication.data.model

import java.util.Date
import kotlinx.serialization.Serializable

enum class ServiceRequestStatus(val displayName: String) {
    PENDING("მუშავდება"),
    ACCEPTED("მიღებულია"),
    IN_PROGRESS("მიმდინარეობს"),
    COMPLETED("დასრულებულია"),
    CANCELLED("გაუქმებულია")
}

@Serializable
data class ServiceRequest(
    val id: String = "",
    val userId: String = "",
    val clientName: String = "",
    val clientPhone: String = "",
    val vehicleId: String = "",
    val serviceType: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val description: String = "",
    val status: String = "PENDING",
    val requestedAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val estimatedArrival: String = "",
    val totalCost: Double = 0.0,
    val paymentMethod: String = "",
    val assignedProviderId: String? = null,
    val serviceProviderPhone: String = ""
)