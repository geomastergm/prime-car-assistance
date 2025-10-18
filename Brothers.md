# Service Provider Login Credentials

## Valid Provider Accounts

### Provider 1 - Towing Specialist
- **Email:** provider1@emergency.ge
- **Password:** provider123
- **Services:** ამოზიდვა, ევაკუაცია

### Provider 2 - Battery & Fuel Specialist  
- **Email:** provider2@emergency.ge
- **Password:** service456
- **Services:** ბატარეა, საწვავი, გუმი

### Admin Account
- **Email:** admin@emergency.ge
- **Password:** admin789
- **Services:** ყველა სერვისი

---

## Client Login Credentials

### Test Client Accounts
- **Phone:** 555123456, **Password:** password123
- **Phone:** 555987654, **Password:** mypass456  
- **Phone:** 555111222, **Password:** secure789

---

## How to Test the Connection

1. **Client App:** Login with any valid phone/password → Create service request
2. **Service Provider App:** Login with provider email/password → See incoming requests
3. **Shared Storage:** Both apps use SharedPreferences to communicate locally

## Features
- ✅ Real authentication validation
- ✅ Local database communication  
- ✅ Request status tracking
- ✅ No Firebase dependency
- ✅ Logout functionality
- ✅ Auto-login on app restart