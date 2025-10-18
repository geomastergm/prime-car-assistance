package com.example.myapplication.data.model

data class CarManufacturer(
    val id: String = "",
    val name: String = "", // მაგ: Toyota, BMW, Nissan
    val country: String = "",
    val logoUrl: String = ""
)

data class CarModel(
    val id: String = "",
    val manufacturerId: String = "",
    val name: String = "", // მაგ: Camry, X5, Qashqai
    val startYear: Int = 0,
    val endYear: Int? = null,
    val bodyType: String = "", // Sedan, SUV, Hatchback
    val fuelTypes: List<String> = emptyList() // Petrol, Diesel, Hybrid, Electric
)

data class CarSeries(
    val id: String = "",
    val modelId: String = "",
    val name: String = "", // მაგ: 2.0 TFSI, 3.5 V6
    val year: Int = 0,
    val engineSize: String = "",
    val power: String = ""
)