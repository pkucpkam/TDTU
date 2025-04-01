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
            borderSide: BorderSide(color: Colors.grey, width: 2),
          ),
          focusedBorder: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
            borderSide: BorderSide(color: Colors.blue, width: 2),
          ),
          labelStyle: TextStyle(color: Colors.grey),
          floatingLabelStyle: TextStyle(color: Colors.blue),
          hintStyle: TextStyle(color: Colors.grey),
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
  bool _obscurePassword = true;
  final FocusNode _usernameFocusNode = FocusNode();

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
      print("Name: ${_usernameController.text}");
      print("Email: ${_emailController.text}");
      print("Age: ${_ageController.text}");
      print("Country: $_country");
      print("Password: ${_passwordController.text}");

      // Xóa form
      _usernameController.clear();
      _emailController.clear();
      _ageController.clear();
      _passwordController.clear();
      setState(() {
        _country = null;
      });

      // Focus lại username
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
      body: Padding(
        padding: const EdgeInsets.all(24.0),

        child: Form(
          key: _formKey,
          autovalidateMode:
              AutovalidateMode.disabled, // chỉ validate khi gọi validate()
          child: ListView(
            children: [
              Center(
                child: Text(
                  'User Registration',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                    color: Colors.blue,
                  ),
                ),
              ),

              SizedBox(height: 16),

              TextFormField(
                controller: _usernameController,
                focusNode: _usernameFocusNode, // Gắn focus node
                decoration: const InputDecoration(
                  labelText: 'Full Name',
                  hintText: 'Enter your name',
                  border: OutlineInputBorder(),
                  prefixIcon: Icon(Icons.person),
                ),
                validator: (value) {
                  if (value == null) {
                    return 'Please enter your name';
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
                    return 'This is not a valid email.';
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
                  counterText: '',
                  prefixIcon: Icon(Icons.calendar_today),
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
                        value == null ? 'Please choose your country.' : null,
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
    );
  }
}
