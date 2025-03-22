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
      appBar: AppBar(title: const Text('User List')),
      body: ListView.separated(
        itemCount: users.length,
        itemBuilder: (context, index) {
          final user = users[index];
          final String name = user["fullName"] ?? "Unknown Name";
          final String jobTitle = user["jobTitle"] ?? "Unknown Job";
          final String avatarUrl =
              "https://randomuser.me/api/portraits/men/${user["id"]}.jpg";

          return ListTile(
            leading: CircleAvatar(
              backgroundImage: NetworkImage(avatarUrl),
              child:
                  avatarUrl.isEmpty
                      ? const Icon(Icons.person)
                      : null, // Placeholder if avatar is missing
            ),
            title: Text(name),
            subtitle: Text(jobTitle),
            trailing: PopupMenuButton<String>(
              onSelected: (String value) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(content: Text('$name selected: $value')),
                );
              },
              itemBuilder: (BuildContext context) {
                return [
                  PopupMenuItem<String>(
                    value: 'Add to favorites',
                    child: Row(
                      children: const [
                        Icon(Icons.favorite),
                        SizedBox(width: 8),
                        Text('Add to favorites'),
                      ],
                    ),
                  ),
                  PopupMenuItem<String>(
                    value: 'Block this user',
                    child: Row(
                      children: const [
                        Icon(Icons.block),
                        SizedBox(width: 8),
                        Text('Block this user'),
                      ],
                    ),
                  ),
                  PopupMenuItem<String>(
                    value: 'Delete this user',
                    child: Row(
                      children: const [
                        Icon(Icons.delete),
                        SizedBox(width: 8),
                        Text('Delete this user'),
                      ],
                    ),
                  ),
                ];
              },
            ),

            onTap: () {},
          );
        },
        separatorBuilder: (context, index) {
          return const Divider();
        },
      ),
    );
  }
}
