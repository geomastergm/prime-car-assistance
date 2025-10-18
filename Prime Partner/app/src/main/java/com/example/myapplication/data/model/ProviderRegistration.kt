package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProviderRegistration(
    val id: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val companyName: String = "",
    val serviceTypes: List<String> = emptyList(),
    val licenseNumber: String = "",
    val experienceYears: Int = 0,
    val workingAreas: List<String> = emptyList(),
    val status: String = "PENDING", // PENDING, APPROVED, REJECTED
    val submittedAt: Long = System.currentTimeMillis(),
    val reviewedAt: Long = 0,
    val reviewedBy: String = "",
    val notes: String = ""
)