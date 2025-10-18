import 'package:firebase_database/firebase_database.dart';

class FirebaseService {
  static final FirebaseDatabase _database = FirebaseDatabase.instanceFor(
    app: Firebase.app(),
    databaseURL: 'https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app',
  );

  static Future<void> initialize() async {
    await _database.setPersistenceEnabled(true);
    await _database.setPersistenceCacheSizeBytes(10000000);
  }

  // Database References
  static DatabaseReference get serviceRequestsRef => _database.ref('service_requests');
  static DatabaseReference get providersRef => _database.ref('providers');
  static DatabaseReference get providerRegistrationsRef => _database.ref('provider_registrations');
  static DatabaseReference get providerWalletsRef => _database.ref('provider_wallets');
  static DatabaseReference get withdrawalRequestsRef => _database.ref('withdrawal_requests');
  static DatabaseReference get bankAccountsRef => _database.ref('bank_accounts');
  static DatabaseReference get paymentsRef => _database.ref('payments');
  
  // Specific references
  static DatabaseReference getProviderRef(String providerId) => providersRef.child(providerId);
  static DatabaseReference getProviderWalletRef(String providerId) => providerWalletsRef.child(providerId);
  static DatabaseReference getBankAccountsRef(String providerId) => bankAccountsRef.child(providerId);
  static DatabaseReference getServiceRequestRef(String requestId) => serviceRequestsRef.child(requestId);
  
  // Queries
  static Query getPendingServiceRequests() {
    return serviceRequestsRef
        .orderByChild('status')
        .equalTo('pending');
  }
  
  static Query getProviderActiveRequests(String providerId) {
    return serviceRequestsRef
        .orderByChild('assignedProviderId')
        .equalTo(providerId);
  }
  
  static Query getWithdrawalRequests(String providerId) {
    return withdrawalRequestsRef
        .orderByChild('providerId')
        .equalTo(providerId);
  }
}
