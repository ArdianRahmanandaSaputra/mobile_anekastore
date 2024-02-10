import 'package:flutter/material.dart';
import 'package:mobile_anekastore/shared/themes.dart';
import 'package:mobile_anekastore/ui/widgets/buttons.dart';
import 'package:mobile_anekastore/ui/widgets/forms.dart';

class SignUpPage extends StatelessWidget {
  const SignUpPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: lightBackgroundColor,
      body: ListView(
        padding: const EdgeInsets.only(bottom: 24, top: 80),
        children: [
          Center(
            child: Column(
              children: [
                 Image.asset(
                  'assets/logo.png',
                  width: 200,
                  height: 200,
                ),
                Text(
                  "Grosir untuk Semua Kebutuhan Anda",
                  style: blackTextStyle.copyWith(
                    fontSize: 16,
                    fontWeight: semiBold,
                  ),
                )
              ],
            ),
          ),
          const SizedBox(height: 0),
          Container(
            margin: const EdgeInsets.symmetric(horizontal: 20),
            decoration: BoxDecoration(
                color: whiteColor, borderRadius: BorderRadius.circular(25)),
            padding: const EdgeInsets.all(22),
            child: Column(
              children: [
                const CustomFormField(title: "Email"),
                const SizedBox(height: 16),
                const CustomFormField(title: "Username"),
                const SizedBox(height: 16),
                const CustomFormField(title: "Password"),
                const SizedBox(height: 30),
                const CustomFilledButton(title: "Sign Up"),
                const SizedBox(
                  height: 4,
                ),
                CustomTextButton(
                  title: "Punya Akun?? Login Disini",
                  onPressed: () {
                    Navigator.pushNamed(context, "/");
                  },
                )
              ],
            ),
          )
        ],
      ),
    );
  }
}
