import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
      ),
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final TextEditingController _controller = TextEditingController();
  final FocusNode _focusNode = FocusNode();
  final List<String> _items = [];

  void _addItem() {
    String text = _controller.text.trim();

    if (text.isEmpty) {
      // Show Snackbar if the textfield is empty
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Please enter some text')));
      return;
    }

    // Add the item to the list and clear the textfield
    setState(() {
      _items.add(text);
      _controller.clear();
    });

    // Focus the textfield again
    _focusNode.requestFocus();
  }

  @override
  void initState() {
    super.initState();
    _focusNode.addListener(() {
      if (_focusNode.hasFocus) {
        // Ensure the keyboard shows up when the TextField gains focus
        FocusScope.of(context).requestFocus(_focusNode);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Container', style: TextStyle(color: Colors.white)),
        centerTitle: true,
        backgroundColor: Colors.blue, // Set AppBar color to blue
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: <Widget>[
            TextField(
              controller: _controller,
              focusNode: _focusNode,
              decoration: InputDecoration(
                labelText: 'Message',
                labelStyle: TextStyle(color: Colors.grey),
                hintText: "Enter a message",
                hintStyle: TextStyle(color: Colors.grey),
                border: OutlineInputBorder(
                  borderSide: BorderSide(color: Colors.black, width: 2),
                ),
                focusedBorder: OutlineInputBorder(
                  borderSide: BorderSide(color: Colors.blue, width: 2),
                ),
                floatingLabelStyle: TextStyle(color: Colors.blue),
              ),

              onSubmitted: (_) => _addItem(),
            ),

            const SizedBox(height: 16),
            SizedBox(
              width: double.infinity,
              child: TextButton(
                onPressed: _addItem,
                style: TextButton.styleFrom(
                  backgroundColor: Colors.blue,
                  padding: EdgeInsets.symmetric(vertical: 20),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(4),
                  ),
                ),
                child: const Text(
                  'Add to list',
                  style: TextStyle(color: Colors.white),
                ),
              ),
            ),
            const SizedBox(height: 16),
            Expanded(
              child: ListView.builder(
                itemCount: _items.length,
                itemBuilder: (context, index) {
                  if (index == 0) {
                    return ListTile(title: Text(_items[index]));
                  } else {
                    return Column(
                      children: [
                        Divider(
                          color: Colors.grey,
                          thickness: 1,
                          height: 1,
                          indent: 10,
                          endIndent: 10,
                        ),
                        ListTile(title: Text(_items[index])),
                      ],
                    );
                  }
                },
              ),
            ),
          ],
        ),
      ),
    );
  }
}
