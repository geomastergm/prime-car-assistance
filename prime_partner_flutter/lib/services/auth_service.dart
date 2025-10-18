import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:prime_partner/services/firebase_service.dart';

class AuthService extends ChangeNotifier {
  final FirebaseAuth _auth = FirebaseAuth.instance;
  User? _user;
  Map<String, dynamic>? _providerData;

  User? get currentUser => _user;
  Map<String, dynamic>? get providerData => _providerData;
  bool get isAuthenticated => _user != null;
  String? get providerId => _user?.uid;

  AuthService() {
    _auth.authStateChanges().listen((User? user) {
      _user = user;
      if (user != null) {
        _loadProviderData(user.uid);
      }
      notifyListeners();
    });
  }

  Future<void> _loadProviderData(String uid) async {
    final snapshot = await FirebaseService.getProviderRef(uid).get();
    if (snapshot.exists) {
      _providerData = Map<String, dynamic>.from(snapshot.value as Map);
      notifyListeners();
    }
  }

  Future<void> signInWithPhone(String phoneNumber) async {
    await _auth.verifyPhoneNumber(
      phoneNumber: phoneNumber,
      verificationCompleted: (PhoneAuthCredential credential) async {
        await _auth.signInWithCredential(credential);
      },
      verificationFailed: (FirebaseAuthException e) {
        throw Exception('Verification failed: ${e.message}');
      },
      codeSent: (String verificationId, int? resendToken) {
        // Store for later verification
      },
      codeAutoRetrievalTimeout: (String verificationId) {},
    );
  }

  Future<void> signOut() async {
    await _auth.signOut();
    _providerData = null;
    notifyListeners();
  }

  Future<void> registerProvider({
    required String name,
    required String phone,
    required String email,
    required List<String> serviceTypes,
    required String idNumber,
  }) async {
    if (_user == null) throw Exception('No authenticated user');

    final providerRef = FirebaseService.getProviderRef(_user!.uid);
    await providerRef.set({
      'id': _user!.uid,
      'name': name,
      'phone': phone,
      'email': email,
      'serviceTypes': serviceTypes,
      'idNumber': idNumber,
      'status': 'pending', // pending, approved, rejected
      'createdAt': DateTime.now().millisecondsSinceEpoch,
      'rating': 0.0,
      'completedJobs': 0,
    });

    // Initialize wallet
    await FirebaseService.getProviderWalletRef(_user!.uid).set({
      'balance': 0.0,
      'totalEarnings': 0.0,
      'pendingAmount': 0.0,
      'updatedAt': DateTime.now().millisecondsSinceEpoch,
    });

    await _loadProviderData(_user!.uid);
  }
}
