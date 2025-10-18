package com.example.myapplication.data.model

data class User(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val isVerified: Boolean = false
)