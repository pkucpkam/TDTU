import 'package:flutter/material.dart';
import 'users.dart'; // Import the users list

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'User List',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const UserListScreen(),
    );
  }
}

class UserListScreen extends StatelessWidget {
  const UserListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('List View', style: TextStyle(color: Colors.white)),
        centerTitle: true,
        backgroundColor: Colors.blue,
      ),
      body: ListView(
        children:
            users.map((user) {
              final String name = user["fullName"] ?? "Unknown Name";
              final String jobTitle = user["jobTitle"] ?? "Unknown Job";
              final String avatarUrl =
                  "https://randomuser.me/api/portraits/men/${user["id"]}.jpg";

              return ListTile(
                leading: CircleAvatar(
                  backgroundImage: NetworkImage(avatarUrl),
                  child: avatarUrl.isEmpty ? const Icon(Icons.person) : null,
                ),
                title: Text(name),
                subtitle: Text(jobTitle),
                onTap: () {
                  final snackBar = SnackBar(
                    content: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text('You\'ve clicked on $name'),
                        TextButton(
                          onPressed: () {
                            ScaffoldMessenger.of(context).hideCurrentSnackBar();
                          },
                          child: Text(
                            'Dismiss',
                            style: TextStyle(color: Colors.yellow),
                          ),
                        ),
                      ],
                    ),
                    behavior:
                        SnackBarBehavior.floating, // Giúp SnackBar nổi lên
                  );

                  ScaffoldMessenger.of(context).showSnackBar(snackBar);
                },
              );
            }).toList(),
      ),
    );
  }
}
