# 📱 Prime Client - Flutter App (iOS + Android)

## 🎯 აღწერა
Prime Car Assistance კლიენტის აპლიკაცია Flutter-ზე დაწერილი.
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
cd prime_client_flutter
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
├── main.dart                 # Entry point
├── config/
│   └── theme.dart           # App theme (Blue)
├── screens/
│   ├── splash_screen.dart   # Splash screen
│   ├── login_screen.dart    # Login/Registration
│   └── home_screen.dart     # Main screen with tabs
├── services/
│   ├── firebase_service.dart # Firebase Database
│   └── auth_service.dart     # Authentication
└── models/
    └── (models here)
```

## 🎨 Features

### ✅ Implemented:
- 🔐 Login/Registration Screen
- 🏠 Home Screen with Service Cards
- 📋 My Requests Tab
- 🚗 Vehicles Tab
- 👤 Profile Tab
- 🔥 Firebase Integration
- 🎨 Modern UI (Blue Theme)

### 🔄 To Be Implemented:
- Service Request Flow
- Real-time Notifications
- Payment Integration
- Maps & Navigation
- Vehicle Management
- Profile Editing

## 🔥 Firebase Integration

**Database URL:**
```
https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app
```

**Nodes:**
- `service_requests` - სერვისის მოთხოვნები
- `users` - მომხმარებლები
- `vehicles` - ავტომობილები
- `payments` - გადახდები

## 📱 გაშვება iOS-ზე

### საჭირო რამ:
1. **Mac კომპიუტერი** (iOS build-ისთვის)
2. **Xcode** (App Store-დან უფასოდ)
3. **Apple Developer Account** ($99/წელიწადი production-ისთვის)

### ნაბიჯები:
```bash
# 1. გახსენი Xcode
open ios/Runner.xcworkspace

# 2. შეცვალე Bundle Identifier
# Runner → Signing & Capabilities → Bundle Identifier: com.prime.client

# 3. აირჩიე Development Team
# Signing & Capabilities → Team → (შენი Apple ID)

# 4. Build
flutter build ios

# 5. Run on Device
flutter run -d <device-id>
```

## 🛠 Development

### Hot Reload:
```bash
# გაუშვი აპლიკაცია
flutter run

# Hot Reload: დააჭირე 'r'
# Hot Restart: დააჭირე 'R'
```

### Debugging:
```bash
flutter run --debug
flutter logs
```

## 📄 License
Private Project - Prime Team

## 👥 Author
Prime Development Team
