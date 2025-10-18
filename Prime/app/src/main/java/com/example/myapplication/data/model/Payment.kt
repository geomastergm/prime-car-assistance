package com.example.myapplication.data.model

enum class PaymentMethod(val displayName: String, val icon: String) {
    CARD("ბანკის ბარათი", "💳"),
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
    val serviceRequestId: String = "",
    
    // ფინანსური დეტალები
    val totalAmount: Double = 0.0,           // სრული თანხა კლიენტისგან
    val platformFee: Double = 0.0,           // პლატფორმის საკომისიო (10-20%)
    val paymentGatewayFee: Double = 0.0,     // Payment Gateway საკომისიო (2-3%)
    val providerAmount: Double = 0.0,        // პროვაიდერის მისაღები თანხა
    
    val paymentMethod: PaymentMethod,
    val status: PaymentStatus = PaymentStatus.PENDING,
    
    // Escrow System - უსაფრთხო დაჯავშნა
    val escrowHeld: Boolean = true,          // დროებით დაბლოკილია
    val escrowReleaseCondition: String = "SERVICE_COMPLETED",
    val escrowReleasedAt: Long? = null,      // როდის გაათავისუფლა
    
    // დროები
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null,
    
    // ტრანზაქციის ID-ები
    val transactionId: String = "",          // Payment Gateway transaction
    val providerPayoutId: String = ""        // Provider-ზე გადარიცხვის ID
)

enum class PaymentStatus(val displayName: String) {
    PENDING("მუშავდება"),
    PROCESSING("მიმდინარეობს"),
    COMPLETED("დასრულებული"),
    FAILED("წარუმატებელი"),
    CANCELLED("გაუქმებული")
}