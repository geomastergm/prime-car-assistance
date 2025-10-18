# 🚀 Prime Car Assistance - Flutter Apps (iOS + Android)

## 📱 პროექტები

### 1. Prime Client (`prime_client_flutter/`)
**აღწერა:** კლიენტის აპლიკაცია  
**ფერი:** 🔵 ლურჯი (#1E3A5F)  
**პლატფორმები:** Android + iOS

**ფუნქციები:**
- ✅ Login/Registration
- ✅ სერვისის მოთხოვნა (მექანიკოსი, საწვავი, ბატარეა, საბურავები)
- ✅ ჩემი მოთხოვნები (Real-time status)
- ✅ ავტომობილების მართვა
- ✅ პროფილი და პარამეტრები
- ✅ გადახდები

### 2. Prime Partner (`prime_partner_flutter/`)
**აღწერა:** პროვაიდერის აპლიკაცია  
**ფერი:** 🟠 ნარინჯისფერი (#FF6B35)  
**პლატფორმები:** Android + iOS

**ფუნქციები:**
- ✅ Provider Registration
- ✅ Real-time Service Requests
- ✅ Wallet System (ციფრული საფულე)
- ✅ Withdrawal Requests (თანხის გატანა)
- ✅ Bank Account Management (11+ ბანკი)
- ✅ Map & Navigation
- ✅ Earnings Dashboard

---

## 🔥 Firebase Backend

**იგივე Firebase Database** რაც Android აპლიკაციებშია!

```
Database URL: https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app
Region: europe-west1 (Belgium)
```

### Nodes:
- `service_requests` - მოთხოვნები
- `users` - მომხმარებლები
- `providers` - პროვაიდერები
- `provider_wallets` - საფულეები
- `withdrawal_requests` - გატანის მოთხოვნები
- `bank_accounts` - საბანკო ანგარიშები (AES encrypted)
- `payments` - გადახდები
- `vehicles` - ავტომობილები

---

## 🛠 Setup & Installation

### 📋 Prerequisites

1. **Flutter SDK** (3.0+)
   ```bash
   # Download: https://flutter.dev/docs/get-started/install
   flutter doctor
   ```

2. **Android Studio** (Android build-ისთვის)
3. **Xcode** (iOS build-ისთვის - მხოლოდ Mac-ზე)
4. **Git**

### ⚙️ Installation Steps

#### 1. Clone Repository
```bash
git clone https://github.com/geomastergm/prime-car-assistance.git
cd prime-car-assistance
```

#### 2. Prime Client Setup
```bash
cd prime_client_flutter
flutter pub get

# Android გაშვება
flutter run

# iOS გაშვება (Mac-ზე)
flutter run -d ios
```

#### 3. Prime Partner Setup
```bash
cd prime_partner_flutter
flutter pub get

# Android გაშვება
flutter run

# iOS გაშვება (Mac-ზე)
flutter run -d ios
```

### 🔧 Firebase Configuration

#### Android:
1. გადი Firebase Console → Project Settings → Your Apps
2. ჩამოტვირთე `google-services.json`
3. დაამატე:
   - `prime_client_flutter/android/app/google-services.json`
   - `prime_partner_flutter/android/app/google-services.json`

#### iOS:
1. გადი Firebase Console → Project Settings → Your Apps → iOS
2. ჩამოტვირთე `GoogleService-Info.plist`
3. დაამატე:
   - `prime_client_flutter/ios/Runner/GoogleService-Info.plist`
   - `prime_partner_flutter/ios/Runner/GoogleService-Info.plist`

---

## 📱 Build & Release

### Android APK:
```bash
# Prime Client
cd prime_client_flutter
flutter build apk --release
# Output: build/app/outputs/flutter-apk/app-release.apk

# Prime Partner
cd prime_partner_flutter
flutter build apk --release
# Output: build/app/outputs/flutter-apk/app-release.apk
```

### iOS (საჭიროა Mac!):
```bash
# Prime Client
cd prime_client_flutter
flutter build ios --release

# Prime Partner
cd prime_partner_flutter
flutter build ios --release

# შემდეგ Xcode-ში გახსენი და Archive → Distribute
```

### iOS Build გარეშე Mac-ის:
**Cloud Build Services:**
- ☁️ Codemagic (https://codemagic.io)
- ☁️ Bitrise (https://bitrise.io)
- ☁️ App Center (Microsoft)

---

## 🎨 UI/UX Design

### Prime Client (Blue Theme)
- **Primary:** #1E3A5F (Navy Blue)
- **Accent:** #2C5F8D (Light Blue)
- **Cards:** Rounded corners, elevation shadows
- **Icons:** Material Design

### Prime Partner (Orange Theme)
- **Primary:** #FF6B35 (Vibrant Orange)
- **Accent:** #FF8C61 (Light Orange)
- **Cards:** Rounded corners, elevation shadows
- **Icons:** Material Design

---

## 💰 Payment System

```
კლიენტი → გადახდა (100 ₾)
    ↓
Escrow Account (პლატფორმა იჭერს)
    ↓
    ├─ პლატფორმის საკომისიო: 15 ₾ (15%)
    ├─ Payment Gateway Fee: 3 ₾ (3%)
    └─ პროვაიდერი იღებს: 82 ₾
        ↓
პროვაიდერის საფულე
    ↓
გატანის მოთხოვნა (Withdrawal)
    ↓
საბანკო გადარიცხვა (1-2 სამუშაო დღე)
```

### Supported Payment Gateways:
- 💳 TBC Pay
- 💳 BOG Pay
- 💳 Stripe (საერთაშორისო)

---

## 🗺 Maps Integration

```dart
// Google Maps API Key საჭიროა!

// Android: android/app/src/main/AndroidManifest.xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY_HERE"/>

// iOS: ios/Runner/AppDelegate.swift
GMSServices.provideAPIKey("YOUR_API_KEY_HERE")
```

**API Key მიიღე:** https://console.cloud.google.com/

---

## 🔒 Security

### Firebase Rules (firebase-rules.json)
```json
{
  "rules": {
    "provider_wallets": {
      "$providerId": {
        ".read": "$providerId === auth.uid || auth.token.admin === true",
        ".write": "$providerId === auth.uid || auth.token.admin === true"
      }
    },
    "bank_accounts": {
      "$providerId": {
        ".read": "$providerId === auth.uid",
        ".write": "$providerId === auth.uid"
      }
    }
  }
}
```

### Bank Account Encryption:
- **Algorithm:** AES-256
- **Key Storage:** Firebase Remote Config (Production)
- **Data:** IBAN, Account Number, Card Numbers

---

## 📊 Analytics & Monitoring

### Firebase Analytics:
```dart
FirebaseAnalytics analytics = FirebaseAnalytics.instance;

// Track events
await analytics.logEvent(
  name: 'service_requested',
  parameters: {
    'service_type': 'mechanic',
    'location': 'Tbilisi',
  },
);
```

### Crashlytics:
```dart
// Automatic crash reporting
FlutterError.onError = FirebaseCrashlytics.instance.recordFlutterError;
```

---

## 🧪 Testing

```bash
# Unit Tests
flutter test

# Integration Tests
flutter drive --target=test_driver/app.dart

# Widget Tests
flutter test test/widget_test.dart
```

---

## 📦 Dependencies

### Core:
- `firebase_core` - Firebase SDK
- `firebase_auth` - Authentication
- `firebase_database` - Realtime Database
- `provider` - State Management

### UI:
- `google_fonts` - Typography
- `cupertino_icons` - iOS-style icons

### Maps:
- `google_maps_flutter` - Google Maps
- `geolocator` - Location services

### Utils:
- `intl` - Internationalization
- `uuid` - Unique IDs
- `encrypt` - AES Encryption
- `shared_preferences` - Local storage

---

## 🚀 Deployment Checklist

### Android:
- [ ] შეცვალე `applicationId` (build.gradle)
- [ ] დაამატე `google-services.json`
- [ ] აიღე Signing Key (Release keystore)
- [ ] Build APK: `flutter build apk --release`
- [ ] Upload to Google Play Console

### iOS:
- [ ] შეცვალე `Bundle Identifier` (Xcode)
- [ ] დაამატე `GoogleService-Info.plist`
- [ ] Setup Apple Developer Account ($99/year)
- [ ] შექმენი App ID & Provisioning Profile
- [ ] Build: `flutter build ios --release`
- [ ] Archive და Upload to App Store Connect

---

## 👥 ორ ლეპტოპზე მუშაობა

### Git Workflow:
```bash
# ლეპტოპი 1 - ცვლილებები
git add .
git commit -m "Added payment screen"
git push

# ლეპტოპი 2 - სინქრონიზაცია
git pull
flutter pub get
```

### Branch Strategy:
```bash
# Feature branch
git checkout -b feature/wallet-system
# Work, commit, push
git push origin feature/wallet-system

# Main branch
git checkout main
git merge feature/wallet-system
```

---

## 🐛 Known Issues & Solutions

### Issue 1: Flutter Doctor Errors
```bash
flutter doctor --android-licenses  # Accept licenses
flutter doctor -v  # Detailed diagnostics
```

### Issue 2: iOS Build Fails (Mac)
```bash
cd ios
pod deintegrate
pod install
cd ..
flutter clean
flutter build ios
```

### Issue 3: Firebase Connection Timeout
- შეამოწმე Internet connection
- Firebase Rules სწორია?
- Database URL სწორია?

---

## 📞 Support & Contact

**Developer:** Prime Team  
**Email:** prime.team@example.com  
**GitHub:** https://github.com/geomastergm/prime-car-assistance

---

## 📄 License

**Private Project** - © 2024-2025 Prime Team  
All rights reserved.

---

## 🎯 Roadmap

### Phase 1 (Current):
- ✅ Basic app structure
- ✅ Firebase integration
- ✅ Authentication
- ✅ UI/UX design

### Phase 2 (Next):
- [ ] Real-time notifications
- [ ] Payment gateway integration
- [ ] In-app chat
- [ ] Advanced maps features

### Phase 3 (Future):
- [ ] AI-powered matching
- [ ] Video call support
- [ ] Multi-language support
- [ ] Analytics dashboard

---

**გაიხარე Flutter development! 🚀**
