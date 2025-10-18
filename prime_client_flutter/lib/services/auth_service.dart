import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:prime_client/services/firebase_service.dart';

class AuthService extends ChangeNotifier {
  final FirebaseAuth _auth = FirebaseAuth.instance;
  User? _user;
  Map<String, dynamic>? _userData;

  User? get currentUser => _user;
  Map<String, dynamic>? get userData => _userData;
  bool get isAuthenticated => _user != null;

  AuthService() {
    _auth.authStateChanges().listen((User? user) {
      _user = user;
      if (user != null) {
        _loadUserData(user.uid);
      }
      notifyListeners();
    });
  }

  Future<void> _loadUserData(String uid) async {
    final snapshot = await FirebaseService.getUserRef(uid).get();
    if (snapshot.exists) {
      _userData = Map<String, dynamic>.from(snapshot.value as Map);
      notifyListeners();
    }
  }

  // Sign In with Phone Number
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
        // Store verificationId for later use
      },
      codeAutoRetrievalTimeout: (String verificationId) {},
    );
  }

  // Sign Out
  Future<void> signOut() async {
    await _auth.signOut();
    _userData = null;
    notifyListeners();
  }

  // Register new user
  Future<void> registerUser({
    required String name,
    required String phone,
    required String email,
  }) async {
    if (_user == null) throw Exception('No authenticated user');

    final userRef = FirebaseService.getUserRef(_user!.uid);
    await userRef.set({
      'id': _user!.uid,
      'name': name,
      'phone': phone,
      'email': email,
      'createdAt': DateTime.now().millisecondsSinceEpoch,
      'role': 'client',
    });

    await _loadUserData(_user!.uid);
  }
}
