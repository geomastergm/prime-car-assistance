# Prime - Car Assistance Platform

🚗 **Prime** და **Prime Partner** - სატრანსპორტო დახმარების სრული პლატფორმა

## 📱 აპლიკაციები

### 1. Prime (Client App)
- **პაკეტი:** com.example.carassistent
- **ფერი:** ლურჯი (#1E3A5F)
- **ფუნქციონალი:**
  - სხვადასხვა სერვისების შეკვეთა (ევაკუაცია, აკუმულატორი, საწვავი, და სხვა)
  - რეალურ დროში პროვაიდერების ნახვა
  - Google Maps ინტეგრაცია
  - გადახდის სისტემა
  - მანქანების მართვა
  - პროფილი და პარამეტრები

### 2. Prime Partner (Provider App)
- **პაკეტი:** com.example.myapplication
- **ფერი:** ნარინჯისფერი (#FF6B35)
- **ფუნქციონალი:**
  - შეკვეთების მიღება რეალურ დროში
  - შეკვეთების დადასტურება/უარყოფა
  - Google Maps ნავიგაცია
  - Provider Wallet სისტემა
  - ბანკის ანგარიშების მართვა
  - გამოტანის მოთხოვნები
  - პროფილი და პარამეტრები

## 🔥 Firebase Architecture

### Realtime Database Structure
```
carassistent-e343a/
├── service_requests/
│   ├── status (indexed)
│   ├── requestedAt (indexed)
│   ├── assignedProviderId (indexed)
│   └── clientId (indexed)
├── users/
├── vehicles/
├── providers/
├── provider_registrations/
├── payments/
├── provider_wallets/
├── withdrawal_requests/
├── bank_accounts/
└── admin_logs/
```

### Database URL
```
https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app/
```

## 💳 Payment System

### Flow
1. **Client გადახდა** → Escrow (15% platform fee + 3% gateway fee)
2. **Service დასრულება** → Provider Wallet
3. **Withdrawal Request** → Bank Transfer

### Supported Banks
- TBC Bank
- Bank of Georgia (BOG)
- Liberty Bank
- Credo Bank
- და სხვა 11+ ქართული ბანკი

## 🔒 Security

- AES-256 encryption for bank account numbers
- Firebase Authentication required
- Role-based access control
- Secure Firebase Rules (see `firebase-rules.json`)

## 🎨 Design

- **Material Design 3**
- **Adaptive Icons**
- **Custom Launcher Icons** - Shield დიზაინით
- **თემები:** ღია/მუქი/სისტემური

## 🛠 Tech Stack

- **Kotlin**
- **Android SDK 34+**
- **Firebase Realtime Database**
- **Firebase Authentication**
- **Google Maps API**
- **Material Design Components**
- **View Binding**
- **Coroutines**

## 📦 Project Structure

```
AndroidStudioProjects/
├── Prime/                      # Client App
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/.../
│   │   │   │   ├── utils/FirebaseHelper.kt
│   │   │   │   ├── SettingsActivity.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── ServiceRequestActivity.kt
│   │   │   │   └── ...
│   │   │   └── res/
│   │   └── google-services.json
│   └── build.gradle.kts
│
├── Prime Partner/              # Provider App
│   ├── app/
│   │   ├── src/main/
│   │   │   ├── java/.../
│   │   │   │   ├── utils/FirebaseHelper.kt
│   │   │   │   ├── SettingsActivity.kt
│   │   │   │   ├── MainActivity.kt
│   │   │   │   └── ...
│   │   │   └── res/
│   │   └── google-services.json
│   └── build.gradle.kts
│
└── firebase-rules.json         # Firebase Security Rules
```

## 🚀 Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or newer
- JDK 17+
- Android SDK 34+
- Firebase Account

### Installation

1. **Clone Repository**
   ```bash
   git clone [repository-url]
   cd AndroidStudioProjects
   ```

2. **Firebase Setup**
   - შექმენით Firebase project: `carassistent-e343a`
   - დაამატეთ Android apps:
     - Prime: `com.example.carassistent`
     - Prime Partner: `com.example.myapplication`
   - ჩასვით `firebase-rules.json` Rules tab-ში

3. **Google Maps API**
   - მიიღეთ API Key Google Cloud Console-დან
   - დაამატეთ `AndroidManifest.xml`-ში

4. **Build Apps**
   ```bash
   # Prime
   cd Prime
   ./gradlew assembleDebug installDebug

   # Prime Partner
   cd "Prime Partner"
   ./gradlew assembleDebug installDebug
   ```

## 📝 Development Workflow

### Git Workflow
```bash
# Pull latest changes
git pull origin main

# Create feature branch
git checkout -b feature/your-feature-name

# Make changes and commit
git add .
git commit -m "Add feature description"

# Push to remote
git push origin feature/your-feature-name

# Create Pull Request on GitHub
```

### Testing
- **Client App Testing:** Use 2 physical devices or 1 device + 1 emulator
- **Provider App Testing:** Monitor service_requests in Firebase Console

## 🐛 Known Issues

- [ ] Google Maps API key configuration needed
- [ ] Payment Gateway integration (TBC Pay, BOG Pay) - TODO
- [ ] Push notifications setup

## 📱 Version Info

- **Prime:** v1.0.0
- **Prime Partner:** v1.0.0
- **Min SDK:** 24
- **Target SDK:** 34

## 👥 Contributors

- Development Team

## 📄 License

Proprietary - All rights reserved

---

**Last Updated:** October 18, 2025
