import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'User Registration',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
        inputDecorationTheme: InputDecorationTheme(
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
            borderSide: const BorderSide(color: Colors.grey, width: 2),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
            borderSide: const BorderSide(color: Colors.blue, width: 2),
          ),
          labelStyle: const TextStyle(color: Colors.grey),
          floatingLabelStyle: const TextStyle(color: Colors.blue),
          hintStyle: const TextStyle(color: Colors.grey),
        ),
      ),
      home: const RegistrationScreen(),
    );
  }
}

class RegistrationScreen extends StatefulWidget {
  const RegistrationScreen({super.key});

  @override
  State<RegistrationScreen> createState() => _RegistrationScreenState();
}

class _RegistrationScreenState extends State<RegistrationScreen> {
  final _formKey = GlobalKey<FormState>();
  final _usernameController = TextEditingController();
  final _passwordController = TextEditingController();
  final _emailController = TextEditingController();
  final _ageController = TextEditingController();
  final FocusNode _usernameFocusNode = FocusNode();
  bool _obscurePassword = true;
  bool _isSubmitting = false;

  String? _country;

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    _emailController.dispose();
    _ageController.dispose();
    _usernameFocusNode.dispose();
    super.dispose();
  }

  void _submitForm() {
    if (_formKey.currentState!.validate()) {
      setState(() {
        _isSubmitting = true;
      });

      // Simulate server delay
      Future.delayed(const Duration(seconds: 3), () {
        setState(() {
          _isSubmitting = false;
        });

        // Show server busy message
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Server is busy, try again later')),
        );
      });

      // Clear form
      _usernameController.clear();
      _emailController.clear();
      _ageController.clear();
      _passwordController.clear();
      setState(() {
        _country = null;
      });

      // Focus username
      FocusScope.of(context).requestFocus(_usernameFocusNode);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Basic Form', style: TextStyle(color: Colors.white)),
        centerTitle: true,
        backgroundColor: Colors.blue,
      ),
      body: Stack(
        children: [
          AbsorbPointer(
            absorbing: _isSubmitting,
            child: Padding(
              padding: const EdgeInsets.all(24.0),
              child: Form(
                key: _formKey,
                autovalidateMode: AutovalidateMode.disabled,
                child: ListView(
                  children: [
                    const Center(
                      child: Text(
                        'User Registration',
                        style: TextStyle(
                          fontSize: 24,
                          fontWeight: FontWeight.bold,
                          color: Colors.blue,
                        ),
                      ),
                    ),
                    const SizedBox(height: 16),
                    TextFormField(
                      controller: _usernameController,
                      focusNode: _usernameFocusNode,
                      decoration: const InputDecoration(
                        labelText: 'Full Name',
                        hintText: 'Enter your name',
                        border: OutlineInputBorder(),
                        prefixIcon: Icon(Icons.person),
                      ),
                      validator: (value) {
                        if (value == null) {
                          return 'Please enter your email.';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),
                    TextFormField(
                      controller: _emailController,
                      decoration: const InputDecoration(
                        labelText: 'Email',
                        hintText: 'Enter your email',
                        border: OutlineInputBorder(),
                        prefixIcon: Icon(Icons.mail),
                      ),
                      keyboardType: TextInputType.emailAddress,
                      validator: (value) {
                        if (value == null || !value.contains('@')) {
                          return 'Enter a valid email address.';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),
                    TextFormField(
                      controller: _ageController,
                      decoration: const InputDecoration(
                        labelText: 'Age',
                        hintText: 'Enter your age',
                        border: OutlineInputBorder(),
                        prefixIcon: Icon(Icons.calendar_today),
                        counterText: '',
                      ),
                      keyboardType: TextInputType.number,
                      buildCounter: null,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return 'Enter your age.';
                        }
                        final age = int.tryParse(value);
                        if (age == null || age < 0 || age > 99) {
                          return 'You must age between 0 and 99.';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16),
                    DropdownButtonFormField<String>(
                      decoration: const InputDecoration(
                        labelText: 'Country',
                        hintText: "Select your country",
                        border: OutlineInputBorder(),
                        prefixIcon: Icon(Icons.flag),
                      ),
                      value: _country,
                      items:
                          ['VietNam', 'Laos', 'Thai']
                              .map(
                                (country) => DropdownMenuItem(
                                  value: country,
                                  child: Text(country),
                                ),
                              )
                              .toList(),
                      onChanged: (value) {
                        setState(() {
                          _country = value;
                        });
                      },
                      validator:
                          (value) =>
                              value == null
                                  ? 'Please choose your country.'
                                  : null,
                    ),
                    const SizedBox(height: 16),
                    TextFormField(
                      controller: _passwordController,
                      obscureText: _obscurePassword,
                      decoration: InputDecoration(
                        labelText: 'Password',
                        hintText: 'Enter your password',
                        border: const OutlineInputBorder(),
                        prefixIcon: const Icon(Icons.lock),
                        suffixIcon: IconButton(
                          icon: Icon(
                            _obscurePassword
                                ? Icons.visibility_off
                                : Icons.visibility,
                          ),
                          onPressed: () {
                            setState(() {
                              _obscurePassword = !_obscurePassword;
                            });
                          },
                        ),
                      ),
                      validator: (value) {
                        if (value == null || value.length < 6) {
                          return 'Password must be at least 6 characters.';
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 24),
                    TextButton(
                      onPressed: _submitForm,
                      style: TextButton.styleFrom(
                        side: const BorderSide(color: Colors.blue, width: 2),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(4),
                        ),
                        padding: const EdgeInsets.symmetric(vertical: 20),
                        backgroundColor: Colors.blue,
                      ),
                      child: const Text(
                        'Register',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ],
                ),
              ),
            ),
          ),

          if (_isSubmitting)
            Container(
              color: Colors.black.withOpacity(0.4),
              child: const Center(
                child: Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    CircularProgressIndicator(),
                    SizedBox(height: 16),
                    Text(
                      'Registering account...',
                      style: TextStyle(color: Colors.white, fontSize: 16),
                    ),
                  ],
                ),
              ),
            ),
        ],
      ),
    );
  }
}
