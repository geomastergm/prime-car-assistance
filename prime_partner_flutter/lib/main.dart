import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:provider/provider.dart';
import 'package:prime_partner/screens/splash_screen.dart';
import 'package:prime_partner/screens/login_screen.dart';
import 'package:prime_partner/screens/home_screen.dart';
import 'package:prime_partner/services/auth_service.dart';
import 'package:prime_partner/config/theme.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  
  // Initialize Firebase
  await Firebase.initializeApp(
    options: const FirebaseOptions(
      apiKey: "AIzaSyDYourApiKey",
      authDomain: "carassistent-e343a.firebaseapp.com",
      databaseURL: "https://carassistent-e343a-default-rtdb.europe-west1.firebasedatabase.app",
      projectId: "carassistent-e343a",
      storageBucket: "carassistent-e343a.appspot.com",
      messagingSenderId: "123456789",
      appId: "1:123456789:android:abcdef",
    ),
  );
  
  runApp(const PrimePartnerApp());
}

class PrimePartnerApp extends StatelessWidget {
  const PrimePartnerApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthService()),
      ],
      child: MaterialApp(
        title: 'Prime Partner',
        debugShowCheckedModeBanner: false,
        theme: AppTheme.lightTheme,
        initialRoute: '/splash',
        routes: {
          '/splash': (context) => const SplashScreen(),
          '/login': (context) => const LoginScreen(),
          '/home': (context) => const HomeScreen(),
        },
      ),
    );
  }
}
