# 🏦 Bank Account Management System - ბანკის ანგარიშების მართვის სისტემა

## 📋 ფუნქციონალი

### ✅ რას შეუძლია Provider-ს:

1. **დაამატოს რამდენიმე ბანკის ანგარიში**
   - TBC Bank
   - Bank of Georgia
   - Liberty Bank
   - Credo Bank
   - და სხვა საქართველოს ბანკები (11+ ბანკი)

2. **მართოს ანგარიშები**
   - დაყენება ძირითად (default) ანგარიშად
   - წაშლა
   - გააქტიურება/გამორთვა

3. **ვერიფიკაცია**
   - ავტომატური ან ხელით (Admin-ის მიერ)
   - 1-2 სამუშაო დღე

4. **უსაფრთხოება**
   - ანგარიშის ნომრების დაშიფვრა (AES)
   - ნაჩვენებია მხოლოდ ბოლო 4 ციფრი

---

## 🔐 უსაფრთხოება

### დაშიფვრა:
```kotlin
Account Number: "GE12TB1234567890123456"
↓ AES Encryption ↓
Stored: "aB3xK9pL2mQ..." (დაშიფრული)
Displayed: "•••• •••• •••• 3456" (ბოლო 4 ციფრი)
```

### ვერიფიკაცია:
- ანგარიში უნდა იყოს Provider-ის სახელზე
- Admin/System ამოწმებს ავთენტურობას
- ვერიფიცირებულ ანგარიშზე გადის withdrawal

---

## 🏦 მხარდაჭერილი ბანკები

| ბანკი | SWIFT კოდი | Icon |
|-------|-----------|------|
| TBC Bank | TBCBGE22 | 🏦 |
| Bank of Georgia | BAGAGE22 | 🏛️ |
| Liberty Bank | LBRTGE22 | 🦅 |
| Credo Bank | CRDOGE22 | 💳 |
| Terabank | TEBAGE22 | 🏢 |
| VTB Bank Georgia | GTBCGE22 | 🏰 |
| Silk Road Bank | SILKGE22 | 🛤️ |
| Halyk Bank Georgia | HLBKGE22 | 🏦 |
| Cartu Bank | CRTUGE22 | 💼 |
| ProCredit Bank | MIBGGE22 | 🏛️ |

---

## 📱 UI Screenshots

### 1. ბანკის ანგარიშების სია:
```
═══════════════════════════════════════════════
🏦 ჩემი ბანკის ანგარიშები
დაამატეთ ბანკის ანგარიში თანხის გამოსატანად
═══════════════════════════════════════════════

┌─────────────────────────────────────────────┐
│  ➕ ახალი ანგარიშის დამატება                │
└─────────────────────────────────────────────┘

┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓ ⭐ Default
┃ 🏦  TBC Bank                                ┃
┃     •••• •••• •••• 1234                     ┃
┃     გიორგი მელაძე                           ┃
┃                                             ┃
┃ ⭐ ძირითადი  ✓ ვერიფიცირებული             ┃
┃                                             ┃
┃ [     ❌     ]                              ┃
┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛

┌─────────────────────────────────────────────┐
│ 🏛️  Bank of Georgia                         │
│     •••• •••• •••• 5678                     │
│     გიორგი მელაძე                           │
│                                             │
│ ✓ ვერიფიცირებული                           │
│                                             │
│ [ ძირითადად დაყენება ]    [  ❌  ]         │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│ 🦅  Liberty Bank                            │
│     •••• •••• •••• 9012                     │
│     გიორგი მელაძე                           │
│                                             │
│ ⏳ ვერიფიკაცია მოლოდინში                    │
│                                             │
│ [     ❌     ]                              │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│ ℹ️ მნიშვნელოვანი ინფორმაცია                │
│                                             │
│ • ვერიფიკაცია: 1-2 სამუშაო დღე              │
│ • ანგარიში უნდა იყოს თქვენს სახელზე         │
│ • თანხა გადაირიცხება მხოლოდ               │
│   ვერიფიცირებულ ანგარიშზე                  │
│ • შეგიძლიათ რამდენიმე ანგარიშის დამატება    │
└─────────────────────────────────────────────┘
```

### 2. ახალი ანგარიშის დამატება (Dialog):
```
═══════════════════════════════════════════════
      🏦 ბანკის ანგარიშის დამატება
═══════════════════════════════════════════════

აირჩიეთ ბანკი
┌─────────────────────────────────────────────┐
│ TBC (თიბისი)                          ▼     │
└─────────────────────────────────────────────┘
  - Bank of Georgia (საქართველოს ბანკი)
  - Liberty Bank (ლიბერთი ბანკი)
  - ...

┌─────────────────────────────────────────────┐
│ ანგარიშის ნომერი                            │
│ [GE12TB1234567890123456]                    │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│ IBAN (არასავალდებულო)                      │
│ [                                ]          │
└─────────────────────────────────────────────┘

┌─────────────────────────────────────────────┐
│ ანგარიშის მფლობელის სახელი და გვარი        │
│ [გიორგი მელაძე                  ]          │
└─────────────────────────────────────────────┘

⚠️ ანგარიში უნდა იყოს თქვენს სახელზე 
   რეგისტრირებული

          [ გაუქმება ]  [ დამატება ]
```

---

## 🔄 Flow

### 1. ანგარიშის დამატება:
```
Provider → "დამატება" → ბანკის არჩევა → ანგარიშის ნომერი
    ↓
Firebase (დაშიფრული)
    ↓
Status: 🟡 Unverified
    ↓
1-2 დღე ვერიფიკაცია
    ↓
Status: ✅ Verified
    ↓
მზადაა Withdrawal-სთვის
```

### 2. Withdrawal-ში გამოყენება:
```
Provider → "თანხის გამოტანა" → აირჩიე ანგარიში (Spinner)
    ↓
მხოლოდ ✅ ვერიფიცირებული ანგარიშები ჩანან
    ↓
80 ₾ → [TBC Bank - ****1234 ▼]
    ↓
[თანხის გამოტანა]
    ↓
Withdrawal → PENDING → PROCESSING → COMPLETED
    ↓
🏦 Provider-ის ბანკში შევიდა
```

### 3. Default ანგარიშის დაყენება:
```
Provider → აირჩიე ანგარიში → "ძირითადად დაყენება"
    ↓
ძველი default → isDefault = false
    ↓
ახალი → isDefault = true (⭐ Badge)
    ↓
შემდეგი Withdrawal-ში ავტომატურად არჩეული
```

---

## 💾 Firebase სტრუქტურა

```
firebase_realtime_database/
└── bank_accounts/
    └── {providerId}/
        ├── {accountId_1}/
        │   ├── id: "account_123"
        │   ├── providerId: "provider_456"
        │   ├── bankName: "TBC Bank"
        │   ├── bankCode: "TBCBGE22"
        │   ├── accountNumber: "aB3xK9pL2mQ..." (დაშიფრული)
        │   ├── accountNumberLast4: "1234"
        │   ├── iban: "GE12TB1234567890123456"
        │   ├── accountHolderName: "გიორგი მელაძე"
        │   ├── isVerified: true
        │   ├── verificationMethod: "document_upload"
        │   ├── verifiedAt: 1697622000000
        │   ├── isDefault: true
        │   ├── isActive: true
        │   └── addedAt: 1697535600000
        │
        ├── {accountId_2}/
        │   ├── bankName: "Bank of Georgia"
        │   ├── isVerified: true
        │   ├── isDefault: false
        │   └── ...
        │
        └── {accountId_3}/
            ├── bankName: "Liberty Bank"
            ├── isVerified: false
            ├── isDefault: false
            └── ...
```

---

## 🛠️ API Methods

### BankAccountService:

```kotlin
// ✅ ანგარიშის დამატება
addBankAccount(
    providerId: String,
    bankName: String,
    accountNumber: String,
    iban: String,
    accountHolderName: String
): Result<BankAccount>

// 📋 ყველა ანგარიშის მიღება
getBankAccounts(providerId: String): Result<List<BankAccount>>

// ⭐ Default-ად დაყენება
setDefaultAccount(
    providerId: String,
    accountId: String
): Result<BankAccount>

// ❌ წაშლა
deleteBankAccount(
    providerId: String,
    accountId: String
): Result<Unit>

// ✅ ვერიფიკაცია (Admin)
verifyBankAccount(
    providerId: String,
    accountId: String,
    verificationMethod: String
): Result<BankAccount>

// 🔄 გააქტიურება/გამორთვა
toggleAccountStatus(
    providerId: String,
    accountId: String,
    isActive: Boolean
): Result<BankAccount>

// ⭐ Default ანგარიშის მიღება
getDefaultAccount(providerId: String): Result<BankAccount?>
```

---

## 🎯 გამოყენება

### 1. Fragment-ში ანგარიშების ჩატვირთვა:
```kotlin
val bankAccountService = BankAccountService()

lifecycleScope.launch {
    val result = bankAccountService.getBankAccounts(providerId)
    if (result.isSuccess) {
        val accounts = result.getOrNull() ?: emptyList()
        displayBankAccounts(accounts)
    }
}
```

### 2. ახალი ანგარიშის დამატება:
```kotlin
lifecycleScope.launch {
    val result = bankAccountService.addBankAccount(
        providerId = "provider_123",
        bankName = "TBC Bank",
        accountNumber = "GE12TB1234567890123456",
        iban = "GE12TB1234567890123456",
        accountHolderName = "გიორგი მელაძე"
    )
    
    if (result.isSuccess) {
        Toast.makeText(context, "✅ დაემატა!", Toast.LENGTH_SHORT).show()
    }
}
```

### 3. Withdrawal-ში გამოყენება:
```kotlin
// ვერიფიცირებული ანგარიშები
val verifiedAccounts = bankAccounts.filter { 
    it.isVerified && it.isActive 
}

// Spinner-ში ჩატვირთვა
val accountNames = verifiedAccounts.map { 
    "${it.bankName} - ****${it.accountNumberLast4}" 
}

// Withdrawal მოთხოვნა
val selectedAccount = verifiedAccounts[selectedPosition]
walletService.requestWithdrawal(
    providerId = providerId,
    amount = 80.0,
    bankAccountId = selectedAccount.id
)
```

---

## ✅ Features

- ✅ 11+ საქართველოს ბანკი
- ✅ რამდენიმე ანგარიშის დამატება
- ✅ AES დაშიფვრა
- ✅ ბოლო 4 ციფრის ჩვენება
- ✅ ვერიფიკაციის სისტემა
- ✅ Default ანგარიში
- ✅ გააქტიურება/გამორთვა
- ✅ SWIFT კოდების ავტომატური დადგენა
- ✅ ბანკის icon-ები
- ✅ უსაფრთხო შენახვა Firebase-ში
- ✅ Withdrawal-თან ინტეგრაცია

---

## 🚀 მზადაა!

Provider-ს შეუძლია:
1. 🏦 დაამატოს ბანკის ანგარიშები
2. ⭐ აირჩიოს ძირითადი
3. 💰 გამოიტანოს თანხა ნებისმიერ ანგარიშზე
4. 📊 მართოს ყველა ანგარიში
5. ✅ მიიღოს ვერიფიკაცია

**შემდეგი**: Bank Transfer API ინტეგრაცია (TBC Pay / BOG Pay)
