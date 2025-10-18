import 'package:firebase_database/firebase_database.dart';

class FirebaseService {
  static final FirebaseDatabase _database = FirebaseDatabase.instanceFor(
    app: Firebase.app(),
    databaseURL: 'https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app',
  );

  // Enable offline persistence
  static Future<void> initialize() async {
    await _database.setPersistenceEnabled(true);
    await _database.setPersistenceCacheSizeBytes(10000000);
  }

  // Database References
  static DatabaseReference get serviceRequestsRef => _database.ref('service_requests');
  static DatabaseReference get usersRef => _database.ref('users');
  static DatabaseReference get vehiclesRef => _database.ref('vehicles');
  static DatabaseReference get providersRef => _database.ref('providers');
  static DatabaseReference get paymentsRef => _database.ref('payments');
  
  // Get specific references
  static DatabaseReference getUserRef(String userId) => usersRef.child(userId);
  static DatabaseReference getVehicleRef(String vehicleId) => vehiclesRef.child(vehicleId);
  static DatabaseReference getServiceRequestRef(String requestId) => serviceRequestsRef.child(requestId);
  
  // Query helpers
  static Query getActiveServiceRequests(String clientId) {
    return serviceRequestsRef
        .orderByChild('clientId')
        .equalTo(clientId);
  }
  
  static Query getUserVehicles(String userId) {
    return vehiclesRef
        .orderByChild('userId')
        .equalTo(userId);
  }
}
