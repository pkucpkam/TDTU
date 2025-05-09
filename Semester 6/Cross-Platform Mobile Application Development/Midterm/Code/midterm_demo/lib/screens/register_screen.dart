import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:midterm_demo/screens/profile_screen.dart';
import 'login_screen.dart';
import 'package:firebase_auth/firebase_auth.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();
  final _confirmPasswordController = TextEditingController();

  bool _isPasswordVisible = false;
  bool _isConfirmPasswordVisible =
      false;
  bool _acceptTerms = false;

  // Hàm đăng ký tài khoản
  Future<void> _register() async {
    if (_formKey.currentState?.validate() ?? false) {
      if (_passwordController.text.trim() ==
              _confirmPasswordController.text.trim() &&
          _acceptTerms) {
        try {
          UserCredential userCredential = await FirebaseAuth.instance
              .createUserWithEmailAndPassword(
                email: _emailController.text.trim(),
                password: _passwordController.text.trim(),
              );

          String uid = userCredential.user!.uid;
          await FirebaseFirestore.instance.collection('users').doc(uid).set({
            'name': _nameController.text,
            'profile_image': '', 
            'friends': [], 
          });

          Navigator.pushReplacement(
            context,
            MaterialPageRoute(builder: (context) => ProfileScreen()),
          );
        } on FirebaseAuthException catch (e) {
          String message = 'An error occurred. Please try again..';
          if (e.code == 'email-already-in-use') {
            message = 'Email already in use.';
          } else if (e.code == 'weak-password') {
            message = 'Password is too weak.';
          } else if (e.code == 'invalid-email') {
            message = 'Invalid email.';
          }
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(message), backgroundColor: Colors.red),
          );
        }
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('Please accept the terms of service'),
            backgroundColor: Colors.red,
          ),
        );
      }
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
                              color: Color(
                                0xFF81D4FA,
                              ).withOpacity(0.1), // Màu xanh dương nhạt
                              shape: BoxShape.circle,
                            ),
                            child: Icon(
                              Icons.message,
                              size: 60,
                              color: Color(0xFF039BE5), // Màu xanh dương nhạt
                            ),
                          ),
                          SizedBox(height: 24),

                          // Registration header
                          Text(
                            'Create a new account',
                            style: TextStyle(
                              fontSize: 28,
                              fontWeight: FontWeight.bold,
                              color: Color(0xFF039BE5), // Màu xanh dương nhạt
                            ),
                          ),
                          SizedBox(height: 12),
                          Text(
                            'Please enter information to register an account',
                            textAlign: TextAlign.center,
                            style: TextStyle(
                              fontSize: 14,
                              color: Colors.grey[600],
                            ),
                          ),
                          SizedBox(height: 32),

                          // Full Name field
                          TextFormField(
                            controller: _nameController,
                            decoration: InputDecoration(
                              labelText: 'Full Name',
                              hintText: 'Please enter your full name',
                              prefixIcon: Icon(
                                Icons.person,
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
                                return 'Please enter your full name';
                              }
                              return null;
                            },
                          ),

                          SizedBox(height: 24),

                          // Email field
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
                                return 'Email invalid';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 24),

                          // Password field with visibility toggle
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
                              if (value.length < 6) {
                                return 'Password must be at least 6 characters';
                              }
                              return null;
                            },
                          ),
                          SizedBox(height: 16),

                          // Confirm Password field with visibility toggle
                          TextFormField(
                            controller: _confirmPasswordController,
                            obscureText:
                                !_isConfirmPasswordVisible, // Thay đổi ở đây
                            decoration: InputDecoration(
                              labelText: 'Confirm password',
                              hintText: 'Confirm password',
                              prefixIcon: Icon(
                                Icons.lock,
                                color: Color(0xFF039BE5), // Màu xanh dương nhạt
                              ),
                              suffixIcon: IconButton(
                                icon: Icon(
                                  _isConfirmPasswordVisible
                                      ? Icons.visibility
                                      : Icons.visibility_off,
                                  color: Color(
                                    0xFF039BE5,
                                  ), // Màu xanh dương nhạt
                                ),
                                onPressed: () {
                                  setState(() {
                                    _isConfirmPasswordVisible =
                                        !_isConfirmPasswordVisible;
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
                                return 'Please confirm your password';
                              }
                              if (value.length < 6) {
                                return 'Password must be at least 6 characters';
                              }
                              return null;
                            },
                          ),

                          SizedBox(height: 24),

                          // Terms and conditions checkbox
                          Row(
                            children: [
                              Checkbox(
                                value: _acceptTerms,
                                onChanged: (value) {
                                  setState(() {
                                    _acceptTerms = value ?? false;
                                  });
                                },
                                activeColor: Color(
                                  0xFF039BE5,
                                ), // Màu xanh dương nhạt
                              ),
                              Expanded(
                                child: Text(
                                  'I agree to the terms of service and privacy policy',
                                  style: TextStyle(fontSize: 12),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(height: 32),

                          // Register button
                          SizedBox(
                            width: double.infinity,
                            height: 50,
                            child: ElevatedButton(
                              onPressed: _register,
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
                                'SIGN UP',
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
                                onTap: () {},
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
                                    FontAwesomeIcons
                                        .google, // Sử dụng biểu tượng FontAwesome cho Google
                                    size: 30,
                                    color: Color(0xFFDB4437),
                                  ),
                                ),
                              ),
                            ],
                          ),
                          SizedBox(height: 32),

                          // Already have account text
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              Text("Already have an account? "),
                              MouseRegion(
                                cursor: SystemMouseCursors.click,
                                child: GestureDetector(
                                  onTap: () {
                                    Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                        builder: (context) => LoginScreen(),
                                      ),
                                    );
                                  },
                                  child: Text(
                                    "Sign In",
                                    style: TextStyle(
                                      color: Color(
                                        0xFF039BE5,
                                      ), // Màu xanh dương nhạt
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
