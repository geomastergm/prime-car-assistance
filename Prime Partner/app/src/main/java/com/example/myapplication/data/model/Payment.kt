package com.example.myapplication.data.model

enum class PaymentMethod(val displayName: String, val icon: String) {
    CARD("ბანკის ბარათი", "💳"),
    TBC_PAY("TBC Pay", "🏦"),
    BOG_PAY("BOG Pay", "🏦"),
    GOOGLE_PAY("Google Pay", "📱"),
    APPLE_PAY("Apple Pay", "🍎"),
    CASH("ნაღდი ფული", "💵"),
    BANK_TRANSFER("ბანკის გადარიცხვა", "🏦")
}

data class PaymentCard(
    val id: String = "",
    val userId: String = "",
    val cardNumber: String = "", // უკანასკნელი 4 ციფრი
    val cardHolderName: String = "",
    val expiryMonth: Int = 0,
    val expiryYear: Int = 0,
    val cardType: String = "", // Visa, Mastercard, Amex
    val isDefault: Boolean = false
)

data class Payment(
    val id: String = "",
    val clientId: String = "",
    val providerId: String = "",
    val serviceId: String = "",
    
    // თანხები
    val serviceAmount: Double = 0.0,
    val gatewayFee: Double = 0.0,
    val totalAmount: Double = 0.0,
    val platformFee: Double = 0.0,
    val providerAmount: Double = 0.0,
    
    val paymentMethod: PaymentMethod,
    val status: PaymentStatus = PaymentStatus.PENDING,
    
    // Escrow ინფორმაცია
    val escrowHeld: Boolean = false,
    val escrowAmount: Double = 0.0,
    val escrowReleaseCondition: String = "",
    val escrowReleasedAt: Long? = null,
    
    val createdAt: Long = System.currentTimeMillis(),
    val paidAt: Long? = null,
    val completedAt: Long? = null,
    val transactionId: String = "",
    val serviceRequestId: String = ""
)

enum class PaymentStatus(val displayName: String) {
    PENDING("მუშავდება"),
    PROCESSING("მიმდინარეობს"),
    COMPLETED("დასრულებული"),
    FAILED("წარუმატებელი"),
    CANCELLED("გაუქმებული"),
    REFUNDED("დაბრუნებული")
}