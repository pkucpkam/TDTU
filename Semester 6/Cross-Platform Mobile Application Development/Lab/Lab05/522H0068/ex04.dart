import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: const ColorScheme.light(primary: Colors.blue),
      ),
      home: const MultiStepForm(),
    );
  }
}

class MultiStepForm extends StatefulWidget {
  const MultiStepForm({super.key});

  @override
  State<MultiStepForm> createState() => _MultiStepFormState();
}

class _MultiStepFormState extends State<MultiStepForm> {
  int _currentStep = 0;
  bool _isComplete = false;

  final _formKeys = [GlobalKey<FormState>(), GlobalKey<FormState>()];

  final _firstNameController = TextEditingController();
  final _lastNameController = TextEditingController();
  final _addressController = TextEditingController();
  final _postalCodeController = TextEditingController();

  void _resetForm() {
    setState(() {
      _isComplete = false;
      _currentStep = 0;
      _firstNameController.clear();
      _lastNameController.clear();
      _addressController.clear();
      _postalCodeController.clear();
    });
  }

  @override
  Widget build(BuildContext context) {
    if (_isComplete) {
      return Scaffold(
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                "Thank You!",
                style: TextStyle(fontSize: 24, color: Colors.green),
              ),
              const SizedBox(height: 20),
              TextButton(
                onPressed: _resetForm,
                style: TextButton.styleFrom(
                  padding: const EdgeInsets.fromLTRB(30, 8, 30, 10),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(4),
                  ),
                  backgroundColor: Colors.blue,
                ),
                child: const Text(
                  "RESET",
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ],
          ),
        ),
      );
    }

    return Scaffold(
      appBar: AppBar(title: const Text("Stepper Form")),
      body: Stepper(
        type: StepperType.horizontal,
        currentStep: _currentStep,
        onStepTapped: (index) {
          if (index < _currentStep) {
            setState(() => _currentStep = index);
          }
        },
        controlsBuilder: (context, details) {
          final isLast = _currentStep == 2;
          return Row(
            children: [
              TextButton(
                onPressed: () {
                  if (_currentStep == 0 || _currentStep == 1) {
                    if (_formKeys[_currentStep].currentState!.validate()) {
                      setState(() => _currentStep += 1);
                    }
                  } else {
                    setState(() => _isComplete = true);
                  }
                },
                style: TextButton.styleFrom(
                  padding: const EdgeInsets.fromLTRB(30, 8, 30, 10),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(4),
                  ),
                  backgroundColor: isLast ? Colors.green : Colors.blue,
                ),
                child: Text(
                  isLast ? 'Finish' : 'Next',
                  style: TextStyle(color: Colors.white),
                ),
              ),
              const SizedBox(width: 12),
              TextButton(
                onPressed: _currentStep == 0 ? null : details.onStepCancel,
                style: TextButton.styleFrom(
                  foregroundColor:
                      _currentStep == 0 ? Colors.grey : Colors.blue,
                  padding: const EdgeInsets.fromLTRB(30, 8, 30, 10),
                ),

                child: const Text("Back"),
              ),
            ],
          );
        },
        onStepCancel: () {
          if (_currentStep > 0) {
            setState(() => _currentStep -= 1);
          }
        },
        steps: [
          Step(
            title: const Text("Personal"),
            isActive: _currentStep >= 0,
            state: _currentStep > 0 ? StepState.complete : StepState.indexed,
            content: Form(
              key: _formKeys[0],
              child: Column(
                children: [
                  TextFormField(
                    controller: _firstNameController,
                    decoration: const InputDecoration(
                      labelText: 'First Name',
                      prefixIcon: Icon(Icons.person, color: Colors.grey),
                    ),
                    validator:
                        (value) =>
                            value == null || value.isEmpty
                                ? 'Please enter first name'
                                : null,
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _lastNameController,
                    decoration: const InputDecoration(
                      labelText: 'Last Name',
                      prefixIcon: Icon(Icons.text_fields, color: Colors.grey),
                    ),
                    validator:
                        (value) =>
                            value == null || value.isEmpty
                                ? 'Please enter last name'
                                : null,
                  ),
                  SizedBox(height: 16),
                ],
              ),
            ),
          ),
          Step(
            title: const Text("Shipping"),
            isActive: _currentStep >= 1,
            state: _currentStep > 1 ? StepState.complete : StepState.indexed,
            content: Form(
              key: _formKeys[1],
              child: Column(
                children: [
                  TextFormField(
                    controller: _addressController,
                    decoration: const InputDecoration(
                      labelText: 'Shipping Address',
                      prefixIcon: Icon(Icons.home, color: Colors.grey),
                    ),
                    validator:
                        (value) =>
                            value == null || value.isEmpty
                                ? 'Please enter address'
                                : null,
                  ),
                  SizedBox(height: 16),
                  TextFormField(
                    controller: _postalCodeController,
                    decoration: const InputDecoration(
                      labelText: 'Postal Code',
                      prefixIcon: Icon(
                        Icons.local_post_office,
                        color: Colors.grey,
                      ),
                    ),
                    validator:
                        (value) =>
                            value == null || value.isEmpty
                                ? 'Please enter postal code'
                                : null,
                  ),
                  SizedBox(height: 16),
                ],
              ),
            ),
          ),
          Step(
            title: const Text("Confirm"),
            isActive: _currentStep == 2,
            state: StepState.indexed,
            content: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Text(
                  "First Name: ${_firstNameController.text}",
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8),
                Text(
                  "Last Name: ${_lastNameController.text}",
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8),
                Text(
                  "Address: ${_addressController.text}",
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 8),
                Text(
                  "Postal Code: ${_postalCodeController.text}",
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 16),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
