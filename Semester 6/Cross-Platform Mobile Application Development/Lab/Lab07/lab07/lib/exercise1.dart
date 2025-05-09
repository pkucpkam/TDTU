import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class Exercise1Screen extends StatefulWidget {
  const Exercise1Screen({super.key});

  @override
  State<Exercise1Screen> createState() => _Exercise1ScreenState();
}

class _Exercise1ScreenState extends State<Exercise1Screen> {
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
                      ? const Icon(Icons.check, color: Colors.blue)
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
    if (picked != null) {
      setState(() {
        _selectedDate = picked;
      });
    }
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
                        activeColor: Colors.blue,
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
    final emailRegex = RegExp(r'^[^@\s]+@[^@\s]+\.[^@\s]+$');
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
    return Theme(
      data: Theme.of(context).copyWith(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
        timePickerTheme: const TimePickerThemeData(
          dialHandColor: Colors.blue,
          dialBackgroundColor: Color(0xFFE3F2FD),
          hourMinuteTextColor: Colors.blue,
        ),

        inputDecorationTheme: const InputDecorationTheme(
          focusedBorder: OutlineInputBorder(
            borderSide: BorderSide(color: Colors.blue, width: 2),
          ),
        ),
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.blue,
            foregroundColor: Colors.white,
          ),
        ),
      ),
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Flutter Dialog'),
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
        ),
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
                  controller: TextEditingController(
                    text: _selectedCountry ?? '',
                  ),
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
      ),
    );
  }
}
