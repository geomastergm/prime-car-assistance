package com.example.myapplication.data.model

data class Vehicle(
    val id: String = "",
    val userId: String = "",
    val make: String = "", // მარკა (მაგ: Toyota)
    val model: String = "", // მოდელი (მაგ: Camry)
    val year: Int = 0, // გამოშვების წელი
    val licensePlate: String = "", // სახელმწიფო ნომერი
    val vin: String = "", // VIN კოდი
    val color: String = "", // ფერი
    val fuelType: String = "", // საწვავის ტიპი (ბენზინი, დიზელი, ელექტრო)
    val isDefault: Boolean = false // ძირითადი მანქანაა თუ არა
)