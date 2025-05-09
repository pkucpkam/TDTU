import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:midterm_demo/firebase_options.dart';
import 'screens/login_screen.dart'; // Hoặc màn hình của bạn

void main() async {
  WidgetsFlutterBinding.ensureInitialized(); // Đảm bảo Flutter đã được khởi tạo
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  ); // Khởi tạo Firebase
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Chat Demo',
      theme: ThemeData(primarySwatch: Colors.blue),
      debugShowCheckedModeBanner: false,
      home: LoginScreen(), // Màn hình đăng nhập của bạn
    );
  }
}
