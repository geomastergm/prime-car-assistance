# 💼 Provider Wallet System - ციფრული საფულის სისტემა

## 🎯 როგორ მუშაობს სისტემა?

### 1️⃣ **Client იხდის სერვისს**
```
Client → Payment Gateway (TBC Pay/BOG/etc.) → Escrow (დაბლოკვა)
```

- Client იხდის მაგ. 100 ლარს
- **3% Gateway Fee**: 3 ლარი → Payment Gateway-ს (TBC, BOG)
- **15% Platform Fee**: 15 ლარი → Prime Platform-ს
- **82% Provider-ის**: 82 ლარი → **Escrow-ში დაიბლოკება** ⏳

### 2️⃣ **სერვისი მიმდინარეობს**
```
Escrow: 82 ლარი (დაბლოკილი) 🔒
Provider-ის Wallet: 0 ლარი (ჯერ არ შევიდა)
```

### 3️⃣ **სერვისი დასრულდა ✅**
```
Escrow გათავისუფლდება → Provider-ის Wallet-ში ჩაირიცხება
```

**Provider-ის Wallet:**
```kotlin
currentBalance: 82 ლარი
availableBalance: 82 ლარი  // გამოსატანად მზადი
pendingBalance: 0 ლარი
```

### 4️⃣ **Provider ითხოვს თანხის გამოტანას**
```
Provider App → "თანხის გამოტანა" → ბანკის ანგარიში → 1-2 დღე
```

**Provider შეუძლია:**
- ნებისმიერ დროს მოითხოვოს withdrawal
- მინ: 10 ლარი
- მაქს: 5000 ლარი ერთ ჯერზე
- უფასოდ (0% საკომისიო)

### 5️⃣ **ბანკში გადარიცხვა**
```
Provider Wallet → Admin Approval → Bank Transfer API → Provider-ის ბანკი
```

---

## 📊 Provider-ის საფულე (Wallet)

### ველები:
```kotlin
data class ProviderWallet(
    val currentBalance: Double,        // ახლანდელი ბალანსი
    val availableBalance: Double,      // გამოსატანად მზადი
    val pendingBalance: Double,        // Escrow-ში დაბლოკილი
    val totalEarned: Double,           // ჯამური შემოსავალი (ყველა დროის)
    val totalWithdrawn: Double,        // ჯამური გამოტანილი თანხა
    val completedServices: Int,        // დასრულებული სერვისები
    val linkedBankAccounts: List<BankAccount>
)
```

### ტრანზაქციის ტიპები:
- ✅ **EARNING**: სერვისიდან შემოსავალი (+)
- 💸 **WITHDRAWAL**: გამოტანილი თანხა (-)
- 🔄 **REFUND**: თანხის დაბრუნება (+)
- 💰 **FEE**: საკომისიოს გადახდა (-)
- 🎁 **BONUS**: პლატფორმის ბონუსი (+)
- ⚙️ **ADJUSTMENT**: ადმინის კორექტირება (±)

---

## 💳 Withdrawal (გამოტანა)

### სტატუსები:
- 🟠 **PENDING**: მოლოდინში (ახალი მოთხოვნა)
- 🔵 **PROCESSING**: მუშავდება (ადმინმა დაამტკიცა)
- 🟢 **APPROVED**: დამტკიცებული (ბანკში გაგზავნილია)
- ✅ **COMPLETED**: დასრულებული (ბანკში შევიდა)
- 🔴 **FAILED**: წარუმატებელი (ბანკის შეცდომა)
- ⛔ **REJECTED**: უარყოფილი (ადმინმა უარყო)
- ⚫ **CANCELLED**: გაუქმებული

### მეთოდები:
- 🏦 **BANK_TRANSFER**: ბანკის გადარიცხვა (1-2 დღე)
- 📱 **TBC_PAY**: TBC Pay (მყისიერი)
- 💳 **BOG_PAY**: BOG Pay (მყისიერი)
- 💰 **CARD**: ბარათზე (1-3 დღე)
- ₿ **CRYPTO**: კრიპტო (15-30 წუთი)

---

## 🔧 ტექნიკური დეტალები

### ფაილები:

#### **Models:**
1. `ProviderWallet.kt` - საფულის მოდელი
2. `WithdrawalRequest.kt` - გამოტანის მოთხოვნა
3. `WalletTransaction.kt` - ტრანზაქცია
4. `BankAccount.kt` - ბანკის ანგარიში

#### **Services:**
1. `WalletService.kt` - საფულის მენეჯმენტი
   - `addEarningToWallet()` - შემოსავლის დამატება
   - `requestWithdrawal()` - გამოტანის მოთხოვნა
   - `processWithdrawal()` - გამოტანის დამუშავება
   - `completeWithdrawal()` - გამოტანის დასრულება
   - `getWalletBalance()` - ბალანსის მიღება
   - `getWithdrawalHistory()` - გამოტანის ისტორია
   - `getTransactionHistory()` - ტრანზაქციების ისტორია

2. `PaymentProcessingService.kt` - გადახდების პროცესი
   - `processPayment()` - გადახდის დამუშავება
   - `releaseEscrowToWallet()` - Escrow → Wallet
   - `requestProviderWithdrawal()` - Provider-ის გამოტანა
   - `approveWithdrawal()` - გამოტანის დამტკიცება
   - `refundPayment()` - რეფანდი

#### **UI:**
1. `ProviderWalletFragment.kt` - საფულის ეკრანი
2. `fragment_provider_wallet.xml` - Layout

---

## 🎨 UI ელემენტები

### მთავარი ბალანსის ბარათი (ნარინჯი):
```
💼 ჩემი საფულე
━━━━━━━━━━━━━━━━━━━
      82 ₾
━━━━━━━━━━━━━━━━━━━
გამოსატანად: 82 ₾  |  მოლოდინში: 0 ₾
```

### სტატისტიკა:
```
📊 სტატისტიკა
━━━━━━━━━━━━━━━━━━━
ჯამური შემოსავალი    გამოტანილი
    500 ₾               418 ₾
```

### თანხის გამოტანა:
```
💰 თანხის გამოტანა
━━━━━━━━━━━━━━━━━━━
[ 82 ₾ ]
[TBC Bank - ****1234 ▼]
[   თანხის გამოტანა   ]
⏱️ თანხა გადაირიცხება 1-2 სამუშაო დღეში
```

### ტრანზაქციების ისტორია:
```
📝 ბოლო ტრანზაქციები
━━━━━━━━━━━━━━━━━━━
┌──────────────────────┐
│ შემოსავალი           │
│ +82 ₾                │
│ სერვისი #12345       │
│ 18 ოქტ 2025, 14:30   │
└──────────────────────┘
```

### გამოტანის ისტორია:
```
🏦 გამოტანის ისტორია
━━━━━━━━━━━━━━━━━━━
┌──────────────────────┐
│ 82 ₾                 │
│ დასრულებული ✅       │
│ TBC Bank - ****1234  │
│ მოთხოვნილია: 17 ოქტ  │
└──────────────────────┘
```

---

## 🔐 უსაფრთხოება

### Escrow სისტემა:
- თანხა დაბლოკილია სანამ სერვისი არ დასრულდება
- მხოლოდ Client-ის დადასტურებით გათავისუფლდება
- თუ პრობლემაა → Refund

### Wallet უსაფრთხოება:
- ყველა ტრანზაქცია ფიქსირდება Firebase-ში
- Balance ყოველთვის კალკულირდება ისტორიიდან
- Admin-ის მხრიდან ვალიდაცია

### Withdrawal უსაფრთხოება:
- მინ/მაქს ლიმიტები
- ბანკის ანგარიშის ვერიფიკაცია
- Admin-ის approval (ან ავტომატური)
- ბანკის ტრანზაქციის ID ტრეკინგი

---

## 📱 Firebase სტრუქტურა

```
firebase_realtime_database/
├── provider_wallets/
│   └── {providerId}/
│       ├── currentBalance: 82.0
│       ├── availableBalance: 82.0
│       ├── pendingBalance: 0.0
│       ├── totalEarned: 500.0
│       ├── totalWithdrawn: 418.0
│       ├── completedServices: 6
│       └── linkedBankAccounts: [...]
│
├── withdrawal_requests/
│   └── {withdrawalId}/
│       ├── amount: 82.0
│       ├── status: "COMPLETED"
│       ├── providerId: "..."
│       ├── bankAccountId: "..."
│       ├── requestedAt: 1697622000000
│       └── completedAt: 1697708400000
│
└── wallet_transactions/
    └── {transactionId}/
        ├── type: "EARNING"
        ├── amount: 82.0
        ├── balanceBefore: 0.0
        ├── balanceAfter: 82.0
        ├── description: "სერვისი #12345"
        └── timestamp: 1697622000000
```

---

## 🚀 გამოყენება

### 1. Provider-მა დაასრულა სერვისი:
```kotlin
val paymentService = PaymentProcessingService()
paymentService.releaseEscrowToWallet(
    paymentId = "payment_123",
    serviceCompleted = true,
    clientApproved = true
)
// ✅ თანხა Provider-ის Wallet-ში შევიდა!
```

### 2. Provider ხედავს ბალანსს:
```kotlin
val walletService = WalletService()
val wallet = walletService.getWalletBalance(providerId)
println("ხელმისაწვდომია: ${wallet.availableBalance} ₾")
```

### 3. Provider ითხოვს გამოტანას:
```kotlin
walletService.requestWithdrawal(
    providerId = "provider_123",
    amount = 82.0,
    bankAccountId = "bank_account_456",
    notes = ""
)
// ✅ Withdrawal მოთხოვნა გაგზავნილია!
```

### 4. Admin/System ამტკიცებს:
```kotlin
walletService.processWithdrawal(
    withdrawalId = "withdrawal_789",
    approve = true,
    note = "დამტკიცებულია"
)
// ✅ ბანკში გადარიცხვა დაიწყო!
```

### 5. ბანკი ადასტურებს:
```kotlin
walletService.completeWithdrawal(
    withdrawalId = "withdrawal_789",
    bankTransactionId = "BANK_TXN_123456"
)
// ✅ თანხა Provider-ის ბანკში შევიდა!
```

---

## ✅ Features

- ✅ Provider-ის ციფრული საფულე აპლიკაციაში
- ✅ ნებისმიერ დროს გამოტანა
- ✅ რეალურ დროში ბალანსის განახლება
- ✅ ტრანზაქციების სრული ისტორია
- ✅ გამოტანის სტატუსის ტრეკინგი
- ✅ მრავალი ბანკის ანგარიში
- ✅ Escrow დაცვა
- ✅ Admin approval (ან auto-approval)
- ✅ ლიმიტების კონტროლი
- ⏳ TBC Pay / BOG Pay ინტეგრაცია (TODO)
- ⏳ ავტომატური Bank Transfer API (TODO)

---

## 📞 დახმარება

თუ რაიმე კითხვა გაქვთ:
- Firebase Console: გახსენი `provider_wallets` და `withdrawal_requests`
- Logs: შეამოწმე `wallet_transactions` ისტორია
- Status: შეამოწმე withdrawal-ის `status` და `statusHistory`

---

## 🎉 მზადაა გამოსაყენებლად!

Provider-ს უკვე აქვს:
1. 💼 საკუთარი საფულე აპლიკაციაში
2. 💰 ნებისმიერ დროს გამოტანის შესაძლებლობა
3. 📊 სრული სტატისტიკა და ისტორია
4. 🏦 ბანკის ანგარიშების მართვა
5. ⏱️ Real-time განახლებები

**შემდეგი ნაბიჯი**: TBC Pay / BOG Pay API ინტეგრაცია რეალური ბანკის გადარიცხვებისთვის!
