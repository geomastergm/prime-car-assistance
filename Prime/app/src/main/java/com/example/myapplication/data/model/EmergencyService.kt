package com.example.myapplication.data.model

enum class ServiceType(val displayNameGeo: String, val displayNameEng: String) {
    TOWING("ბუქსირება", "Towing"),
    EVACUATION("ევაკუატორი", "Evacuation"),
    BATTERY_JUMP("აკუმულატორის დახმარება", "Battery Jump Start"),
    FUEL_DELIVERY("საწვავის მიწოდება", "Fuel Delivery"),
    TIRE_CHANGE("გუმის შეცვლა", "Tire Change"),
    LOCKOUT_SERVICE("მანქანაში ჩაკეტვისას", "Lockout Service"),
    MINOR_REPAIR("მცირე შეკეთება", "Minor Repair"),
    WINCH_OUT("ჩაბჭერიდან ამოღება", "Winch Out")
}

data class EmergencyService(
    val id: String = "",
    val serviceType: ServiceType,
    val title: String = "",
    val description: String = "",
    val estimatedPrice: Double = 0.0,
    val estimatedTime: String = "", // მაგ: "15-30 წუთი"
    val phoneNumber: String = "",
    val isAvailable: Boolean = true
)