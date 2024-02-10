import 'package:flutter/material.dart';
import 'package:mobile_anekastore/ui/pages/sign_in_page.dart';
import 'package:mobile_anekastore/ui/pages/sign_up_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      routes: {
        '/' : (context) => const SignInPage(),
        '/sign-up' : (context) => const SignUpPage()
      },
    );
  }
}

