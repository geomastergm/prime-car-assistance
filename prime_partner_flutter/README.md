# 🔧 Prime Partner - Flutter App (iOS + Android)

## 🎯 აღწერა
Prime Car Assistance პროვაიდერის აპლიკაცია Flutter-ზე დაწერილი.
მხარს უჭერს როგორც **iOS** ასევე **Android** პლატფორმებს.

## 🚀 Setup Instructions

### 1. Flutter SDK დაინსტალება
```bash
# Download Flutter SDK
# Windows: https://docs.flutter.dev/get-started/install/windows
# Mac: https://docs.flutter.dev/get-started/install/macos

# Verify installation
flutter doctor
```

### 2. Dependencies დაინსტალება
```bash
cd prime_partner_flutter
flutter pub get
```

### 3. Firebase Configuration
**Android:**
- დაამატე `google-services.json` → `android/app/`

**iOS:**
- დაამატე `GoogleService-Info.plist` → `ios/Runner/`

### 4. გაშვება
```bash
# Android
flutter run

# iOS (საჭიროა Mac!)
flutter run -d ios

# Build APK
flutter build apk --release

# Build iOS (Mac-ზე)
flutter build ios --release
```

## 📦 პროექტის სტრუქტურა

```
lib/
├── main.dart                  # Entry point
├── config/
│   └── theme.dart            # App theme (Orange)
├── screens/
│   ├── splash_screen.dart    # Splash screen
│   ├── login_screen.dart     # Login
│   └── home_screen.dart      # Main screen
├── services/
│   ├── firebase_service.dart # Firebase Database
│   ├── auth_service.dart     # Authentication
│   └── wallet_service.dart   # Wallet & Payments
└── models/
    └── (models here)
```

## 🎨 Features

### ✅ Core Features:
- 🔐 Provider Login/Registration
- 📋 Real-time Service Requests
- 💰 Wallet System
- 🏦 Bank Account Management  
- 💸 Withdrawal Requests
- 📊 Earnings Dashboard
- 🗺 Map & Navigation
- 🎨 Modern UI (Orange Theme)

### 🔄 To Be Implemented:
- Real-time Request Notifications
- In-app Chat with Clients
- Payment Gateway Integration
- Route Optimization
- Analytics Dashboard

## 🔥 Firebase Integration

**Database URL:**
```
https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app
```

**Provider Nodes:**
- `service_requests` - მოთხოვნები
- `providers` - პროვაიდერები
- `provider_wallets` - საფულე
- `withdrawal_requests` - თანხის გატანა
- `bank_accounts` - საბანკო ანგარიშები (AES encrypted)
- `payments` - გადახდები

## 💰 Wallet System

### Payment Flow:
```
Client Payment (100 ₾)
    ↓
Escrow (Platform holds)
    ↓
- Platform Fee: 15 ₾ (15%)
- Gateway Fee: 3 ₾ (3%)
- Provider Gets: 82 ₾
    ↓
Provider Wallet
    ↓
Withdrawal Request
    ↓
Bank Transfer (1-2 business days)
```

### Supported Banks:
- 🏦 TBC Bank
- 🏦 Bank of Georgia
- 🏦 Liberty Bank
- 🏦 Credo Bank
- და სხვა...

## 📱 გაშვება iOS-ზე

### საჭირო რამ:
1. **Mac კომპიუტერი** (iOS build-ისთვის)
2. **Xcode** (App Store-დან უფასოდ)
3. **Apple Developer Account** ($99/წელიწადი)

### ნაბიჯები:
```bash
# 1. გახსენი Xcode
open ios/Runner.xcworkspace

# 2. შეცვალე Bundle Identifier
# Runner → Signing & Capabilities → Bundle Identifier: com.prime.partner

# 3. აირჩიე Development Team
# Signing & Capabilities → Team → (შენი Apple ID)

# 4. Build
flutter build ios

# 5. Run on Device
flutter run -d <device-id>
```

## 🔒 Security

### Bank Account Encryption:
```dart
// AES-256 encryption for bank account data
final encrypted = encryptor.encrypt(accountNumber);
// Stored in Firebase with encryption
```

### Firebase Rules:
- Provider-specific access control
- Admin privileges for sensitive operations
- Wallet read/write permissions per provider

## 🛠 Development

### Hot Reload:
```bash
flutter run
# Hot Reload: 'r'
# Hot Restart: 'R'
```

### Testing:
```bash
flutter test
flutter drive --target=test_driver/app.dart
```

## 📊 Analytics

Track provider performance:
- Total earnings
- Completed requests
- Average rating
- Response time

## 📄 License
Private Project - Prime Team

## 👥 Author
Prime Development Team
