import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:provider/provider.dart';
import 'package:prime_client/screens/splash_screen.dart';
import 'package:prime_client/screens/login_screen.dart';
import 'package:prime_client/screens/home_screen.dart';
import 'package:prime_client/services/auth_service.dart';
import 'package:prime_client/config/theme.dart';

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
  
  runApp(const PrimeClientApp());
}

class PrimeClientApp extends StatelessWidget {
  const PrimeClientApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (_) => AuthService()),
      ],
      child: MaterialApp(
        title: 'Prime - Car Assistance',
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
