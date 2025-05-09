import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:google_sign_in/google_sign_in.dart';
import 'package:midterm_demo/screens/chat_list_screen.dart';
import 'package:midterm_demo/screens/profile_screen.dart';
import 'package:midterm_demo/screens/register_screen.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _formKey = GlobalKey<FormState>();
  bool _isPasswordVisible = false;
  bool _rememberMe = false;

  final FirebaseAuth _auth = FirebaseAuth.instance;
  Future<void> _signInWithEmailAndPassword() async {
    try {
      await _auth.signInWithEmailAndPassword(
        email: _emailController.text.trim(),
        password: _passwordController.text.trim(),
      );
      User? currentUser = FirebaseAuth.instance.currentUser;
      if (currentUser != null) {
        await FirebaseDatabase.instance
            .ref("user_status/${currentUser.uid}")
            .set(true);
      }

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Log in successfully!'),
          backgroundColor: Colors.green,
          duration: Duration(seconds: 2),
        ),
      );
      await Future.delayed(Duration(seconds: 1));

      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => ChatListScreen()),
      );
    } on FirebaseAuthException {
      String errorMessage = 'Incorrect account or password';

      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(errorMessage), backgroundColor: Colors.red),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('An error has occurred: $e'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  Future<void> _signInWithGoogle() async {
    try {
      final GoogleSignInAccount? googleUser = await GoogleSignIn().signIn();
      if (googleUser == null) return; // Người dùng hủy đăng nhập

      final GoogleSignInAuthentication googleAuth =
          await googleUser.authentication;

      final credential = GoogleAuthProvider.credential(
        accessToken: googleAuth.accessToken,
        idToken: googleAuth.idToken,
      );

      // Đăng nhập Firebase bằng credential Google
      final UserCredential userCredential = await FirebaseAuth.instance
          .signInWithCredential(credential);

      // Cập nhật trạng thái online trong Realtime Database
      await FirebaseDatabase.instance
          .ref("user_status/${userCredential.user!.uid}")
          .set(true);

      final user = userCredential.user;

      if (user != null) {
        final userDoc = FirebaseFirestore.instance
            .collection('users')
            .doc(user.uid);

        final snapshot = await userDoc.get();

        // Nếu người dùng chưa tồn tại trong Firestore thì lưu thông tin
        if (!snapshot.exists) {
          await userDoc.set({
            'name': user.displayName ?? '',
            'email': user.email,
            'profile_image': user.photoURL ?? '',
            'friends': [],
          });

          // Chuyển sang màn hình Profile hoặc màn hình thiết lập
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => ProfileScreen()),
          );
        } else {
          // Nếu người dùng đã tồn tại, chuyển thẳng vào màn hình chat
          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => ChatListScreen()),
          );
        }
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Google Sign In Failed: $e'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  Future<void> resetPasswordFromDialog(String email) async {
    if (email.trim().isEmpty) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Please enter your email')));
      return;
    }

    try {
      await FirebaseAuth.instance.sendPasswordResetEmail(email: email.trim());
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Password reset email sent to $email'),
          backgroundColor: Colors.green,
        ),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Error sending email: $e'),
          backgroundColor: Colors.red,
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Color(0xFF81D4FA),
              Color(0xFF039BE5),
            ], // Màu xanh dương nhạt
          ),
        ),
        child: SafeArea(
          child: Center(
            child: SingleChildScrollView(
              child: Padding(
                padding: const EdgeInsets.all(24.0),
                child: Card(
                  elevation: 8,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(16),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(24.0),
                    child: Form(
                      key: _formKey,
                      child: Column(
                        mainAxisSize: MainAxisSize.min,
                        children: [
                          // Logo or App icon
                          Container(
                            height: 100,
                            width: 100,
                            decoration: BoxDecoration(
                              color: Color(0xFF81D4FA).withOpacity(0.1),
                              shape: BoxShape.circle,
                            ),
                            child: Icon(
                              Icons.message,
                              size: 60,
                              color: Color(0xFF039BE5), // Màu xanh dương nhạt
                            ),
                          ),
                          SizedBox(height: 24),

                          // Welcome text
                          Text(
                            'Welcome back',
                            style: TextStyle(
                              fontSize: 28,
                              fontWeight: FontWeight.bold,
                              color: Color(0xFF039BE5), // Màu xanh dương nhạt
                            ),
                          ),
                          SizedBox(height: 12),
                          SizedBox(height: 32),

                          // Email field with animation
                          TextFormField(
                            controller: _emailController,
                            keyboardType: TextInputType.emailAddress,
                            decoration: InputDecoration(
                              labelText: 'Email',
                              hintText: 'Enter your email',
                              prefixIcon: Icon(
                                Icons.email,
                                color: Color(0xFF039BE5), // Màu xanh dương nhạt
                              ),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                                borderSide: BorderSide(
                                  color: Color(
                                    0xFF039BE5,
                                  ), // Màu xanh dương nhạt
                                ),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                                borderSide: BorderSide(
                                  color: Color(
                                    0xFF039BE5,
                                  ), // Màu xanh dương nhạt
                                  width: 2,
                                ),
                              ),
                              floatingLabelStyle: TextStyle(
                                color: Color(0xFF039BE5), // Màu xanh dương nhạt
                              ),
                            ),
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Please enter your email';
                              }
                              if (!RegExp(
                                r'^[^@]+@[^@]+\.[^@]+',
                              ).hasMatch(value)) {
                                return 'Email not valid';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 24),

                          // Password field with toggle visibility
                          TextFormField(
                            controller: _passwordController,
                            obscureText: !_isPasswordVisible,
                            decoration: InputDecoration(
                              labelText: 'Password',
                              hintText: 'Enter your password',
                              prefixIcon: Icon(
                                Icons.lock,
                                color: Color(0xFF039BE5), // Màu xanh dương nhạt
                              ),
                              suffixIcon: IconButton(
                                icon: Icon(
                                  _isPasswordVisible
                                      ? Icons.visibility
                                      : Icons.visibility_off,
                                  color: Color(
                                    0xFF039BE5,
                                  ), // Màu xanh dương nhạt
                                ),
                                onPressed: () {
                                  setState(() {
                                    _isPasswordVisible = !_isPasswordVisible;
                                  });
                                },
                              ),
                              border: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                                borderSide: BorderSide(
                                  color: Color(
                                    0xFF039BE5,
                                  ), // Màu xanh dương nhạt
                                ),
                              ),
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(12),
                                borderSide: BorderSide(
                                  color: Color(
                                    0xFF039BE5,
                                  ), // Màu xanh dương nhạt
                                  width: 2,
                                ),
                              ),
                              floatingLabelStyle: TextStyle(
                                color: Color(0xFF039BE5), // Màu xanh dương nhạt
                              ),
                            ),
                            validator: (value) {
                              if (value == null || value.isEmpty) {
                                return 'Please enter your password';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 8),

                          // Remember me & Forgot password row
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Row(
                                children: [
                                  Checkbox(
                                    value: _rememberMe,
                                    onChanged: (value) {
                                      setState(() {
                                        _rememberMe = value ?? false;
                                      });
                                    },
                                    activeColor: Color(
                                      0xFF039BE5,
                                    ), // Màu xanh dương nhạt
                                  ),
                                  Text('Remember me'),
                                ],
                              ),
                              TextButton(
                                onPressed: () {
                                  final TextEditingController
                                  emailDialogController =
                                      TextEditingController();
                                  showDialog(
                                    context: context,
                                    barrierDismissible: false,
                                    builder: (BuildContext context) {
                                      return AlertDialog(
                                        shape: RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(
                                            20,
                                          ),
                                        ),
                                        titlePadding: const EdgeInsets.fromLTRB(
                                          24,
                                          24,
                                          24,
                                          0,
                                        ),
                                        contentPadding:
                                            const EdgeInsets.fromLTRB(
                                              24,
                                              12,
                                              24,
                                              24,
                                            ),
                                        title: Row(
                                          children: const [
                                            Icon(
                                              Icons.lock_reset,
                                              color: Color(0xFF039BE5),
                                            ),
                                            SizedBox(width: 8),
                                            Text(
                                              "Password recovery",
                                              style: TextStyle(
                                                fontWeight: FontWeight.bold,
                                                fontSize: 18,
                                                color: Color(0xFF039BE5),
                                              ),
                                            ),
                                          ],
                                        ),
                                        content: Column(
                                          mainAxisSize: MainAxisSize.min,
                                          children: [
                                            const Text(
                                              "Enter email to receive password recovery link:",
                                              style: TextStyle(fontSize: 14),
                                            ),
                                            const SizedBox(height: 12),
                                            TextField(
                                              controller: emailDialogController,
                                              autofocus: true,
                                              keyboardType:
                                                  TextInputType.emailAddress,
                                              decoration: InputDecoration(
                                                hintText: 'example@gmail.com',
                                                border: OutlineInputBorder(
                                                  borderRadius:
                                                      BorderRadius.circular(12),
                                                ),
                                                focusedBorder:
                                                    OutlineInputBorder(
                                                      borderSide:
                                                          const BorderSide(
                                                            color: Color(
                                                              0xFF039BE5,
                                                            ),
                                                          ),
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                            12,
                                                          ),
                                                    ),
                                              ),
                                            ),
                                          ],
                                        ),
                                        actions: [
                                          TextButton(
                                            onPressed:
                                                () => Navigator.pop(context),
                                            child: const Text('Cancel'),
                                          ),
                                          ElevatedButton(
                                            style: ElevatedButton.styleFrom(
                                              backgroundColor: Color(
                                                0xFF039BE5,
                                              ),
                                              shape: RoundedRectangleBorder(
                                                borderRadius:
                                                    BorderRadius.circular(12),
                                              ),
                                            ),
                                            onPressed: () {
                                              final email =
                                                  emailDialogController.text
                                                      .trim();
                                              resetPasswordFromDialog(email);
                                              Navigator.pop(context);
                                            },
                                            child: const Text('Confirm'),
                                          ),
                                        ],
                                      );
                                    },
                                  );
                                },

                                child: const Text(
                                  'Forgot password?',
                                  style: TextStyle(
                                    color: Color(0xFF039BE5),
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(height: 32),

                          // Login button with gradient
                          SizedBox(
                            width: double.infinity,
                            height: 50,
                            child: ElevatedButton(
                              onPressed: () {
                                if (_formKey.currentState?.validate() ??
                                    false) {
                                  _signInWithEmailAndPassword();
                                }
                              },
                              style: ElevatedButton.styleFrom(
                                backgroundColor: Color(
                                  0xFF039BE5,
                                ), // Màu xanh dương nhạt
                                foregroundColor: Colors.white,
                                elevation: 4,
                                shape: RoundedRectangleBorder(
                                  borderRadius: BorderRadius.circular(12),
                                ),
                              ),
                              child: Text(
                                'SIGN IN',
                                style: TextStyle(
                                  fontSize: 16,
                                  fontWeight: FontWeight.bold,
                                ),
                              ),
                            ),
                          ),
                          SizedBox(height: 24),

                          // OR divider
                          Row(
                            children: [
                              Expanded(child: Divider()),
                              Padding(
                                padding: const EdgeInsets.symmetric(
                                  horizontal: 16,
                                ),
                                child: Text('OR'),
                              ),
                              Expanded(child: Divider()),
                            ],
                          ),
                          SizedBox(height: 24),

                          // Social login buttons
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              // Facebook button
                              InkWell(
                                onTap: () {},
                                child: Container(
                                  padding: EdgeInsets.all(10),
                                  decoration: BoxDecoration(
                                    color: Color(0xFF1877F2),
                                    shape: BoxShape.circle,
                                    boxShadow: [
                                      BoxShadow(
                                        color: Colors.black26,
                                        offset: Offset(0, 2),
                                        blurRadius: 6.0,
                                      ),
                                    ],
                                  ),
                                  child: FaIcon(
                                    FontAwesomeIcons
                                        .facebook, // Sử dụng biểu tượng FontAwesome cho Facebook
                                    size: 30,
                                    color: Colors.white,
                                  ),
                                ),
                              ),
                              SizedBox(width: 24),

                              // Google button
                              InkWell(
                                onTap: _signInWithGoogle,
                                child: Container(
                                  padding: EdgeInsets.all(10),
                                  decoration: BoxDecoration(
                                    color: Colors.white,
                                    shape: BoxShape.circle,
                                    boxShadow: [
                                      BoxShadow(
                                        color: Colors.black26,
                                        offset: Offset(0, 2),
                                        blurRadius: 6.0,
                                      ),
                                    ],
                                  ),
                                  child: FaIcon(
                                    FontAwesomeIcons.google,
                                    size: 30,
                                    color: Color(0xFFDB4437),
                                  ),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(height: 32),

                          // Sign up text
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Text(
                                "Don't have an account yet? ",
                                style: TextStyle(color: Colors.grey[600]),
                              ),
                              MouseRegion(
                                cursor: SystemMouseCursors.click,
                                child: GestureDetector(
                                  onTap: () {
                                    Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => RegisterScreen(),
                                      ),
                                    );
                                  },
                                  child: Text(
                                    "Sign up now",
                                    style: TextStyle(
                                      color: Color(0xFF039BE5),
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }
}
