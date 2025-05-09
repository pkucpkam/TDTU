import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Dialog Form',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        primaryColor: Colors.blue, // Màu chính
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white, // Màu chữ trên AppBar
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.blue, // Nút màu xanh dương
            foregroundColor: Colors.white,
          ),
        ),
        inputDecorationTheme: const InputDecorationTheme(
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.blue),
          ),
        ),
      ),

      home: const RegistrationForm(),
    );
  }
}

class RegistrationForm extends StatefulWidget {
  const RegistrationForm({super.key});

  @override
  State<RegistrationForm> createState() => _RegistrationFormState();
}

class _RegistrationFormState extends State<RegistrationForm> {
  final _formKey = GlobalKey<FormState>();

  final _nameController = TextEditingController();
  final _emailController = TextEditingController();
  String? _selectedCountry;
  DateTime? _selectedDate;
  TimeOfDay? _selectedTime;
  String? _selectedJob;

  final List<String> _countries = [
    'Vietnam',
    'USA',
    'India',
    'Kazakhstan',
    'Japan',
    'Germany',
  ];

  final List<String> _jobs = ['Teacher', 'Engineer', 'Doctor', 'Designer'];

  InputDecoration _buildInputDecoration(String label) {
    return InputDecoration(
      labelText: label,
      border: const OutlineInputBorder(),
      contentPadding: const EdgeInsets.symmetric(vertical: 12, horizontal: 12),
    );
  }

  Future<void> _selectCountry() async {
    await showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        return ListView.builder(
          itemCount: _countries.length,
          itemBuilder: (context, index) {
            return ListTile(
              title: Text(_countries[index]),
              trailing:
                  _selectedCountry == _countries[index]
                      ? const Icon(Icons.check)
                      : null,
              onTap: () {
                setState(() {
                  _selectedCountry = _countries[index];
                });
                Navigator.pop(context);
              },
            );
          },
        );
      },
    );
  }

  Future<void> _selectDate() async {
    final currentYear = DateTime.now().year;
    final picked = await showDatePicker(
      context: context,
      initialDate: DateTime(currentYear - 18),
      firstDate: DateTime(currentYear - 99),
      lastDate: DateTime(currentYear - 18),
    );
    if (picked == null) {
      return;
    }
    setState(() {
      _selectedDate = picked;
    });
  }

  Future<void> _selectTime() async {
    final picked = await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
    );
    if (picked != null) {
      setState(() {
        _selectedTime = picked;
      });
    }
  }

  Future<void> _selectJob() async {
    await showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Select Job'),
          content: StatefulBuilder(
            builder: (BuildContext context, StateSetter setStateDialog) {
              return Column(
                mainAxisSize: MainAxisSize.min,
                children:
                    _jobs.map((job) {
                      return RadioListTile<String>(
                        title: Text(job),
                        value: job,
                        groupValue: _selectedJob,
                        onChanged: (value) {
                          setState(() {
                            _selectedJob = value;
                          });
                          setStateDialog(() {});
                          Navigator.pop(context);
                        },
                      );
                    }).toList(),
              );
            },
          ),
        );
      },
    );
  }

  String? _validateEmail(String? value) {
    if (value == null || value.isEmpty) return 'Please enter email';
    final emailRegex = RegExp(r'^[^@\s]+@[^@\s]+\.[^@\s]+\$');
    if (!emailRegex.hasMatch(value)) return 'Invalid email address';
    return null;
  }

  void _submitForm() {
    if (_formKey.currentState!.validate() &&
        _selectedCountry != null &&
        _selectedDate != null &&
        _selectedTime != null &&
        _selectedJob != null) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Registration Successful!')));
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text('Please fill all fields correctly.')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Flutter Dialog')),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Form(
          key: _formKey,
          child: ListView(
            children: [
              TextFormField(
                controller: _nameController,
                decoration: _buildInputDecoration('Your Name'),
                validator:
                    (value) =>
                        value == null || value.isEmpty
                            ? 'Please enter name'
                            : null,
              ),
              const SizedBox(height: 10),
              TextFormField(
                controller: _emailController,
                decoration: _buildInputDecoration('Email Address'),
                validator: _validateEmail,
              ),
              const SizedBox(height: 10),
              TextFormField(
                readOnly: true,
                decoration: _buildInputDecoration(
                  'Your country',
                ).copyWith(suffixIcon: const Icon(Icons.arrow_drop_down)),
                controller: TextEditingController(text: _selectedCountry ?? ''),
                onTap: _selectCountry,
              ),

              const SizedBox(height: 10),
              TextFormField(
                readOnly: true,
                decoration: _buildInputDecoration(
                  'Birthday',
                ).copyWith(suffixIcon: const Icon(Icons.arrow_drop_down)),
                controller: TextEditingController(
                  text:
                      _selectedDate != null
                          ? DateFormat('dd/MM/yyyy').format(_selectedDate!)
                          : '',
                ),
                onTap: _selectDate,
              ),

              const SizedBox(height: 10),
              TextFormField(
                readOnly: true,
                decoration: _buildInputDecoration(
                  'Birthtime',
                ).copyWith(suffixIcon: const Icon(Icons.arrow_drop_down)),
                controller: TextEditingController(
                  text:
                      _selectedTime != null
                          ? _selectedTime!.format(context)
                          : '',
                ),
                onTap: _selectTime,
              ),

              const SizedBox(height: 10),
              TextFormField(
                readOnly: true,
                decoration: _buildInputDecoration(
                  'Job',
                ).copyWith(suffixIcon: const Icon(Icons.arrow_drop_down)),
                controller: TextEditingController(text: _selectedJob ?? ''),
                onTap: _selectJob,
              ),

              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: _submitForm,
                style: ElevatedButton.styleFrom(
                  padding: const EdgeInsets.symmetric(vertical: 24),
                  textStyle: const TextStyle(fontSize: 16),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                ),
                child: const Text('Register'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
