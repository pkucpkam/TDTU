import 'package:flutter/material.dart';
import 'users.dart'; // Import danh sách người dùng

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

class UserListScreen extends StatefulWidget {
  const UserListScreen({super.key});

  @override
  _UserListScreenState createState() => _UserListScreenState();
}

class _UserListScreenState extends State<UserListScreen> {
  List<Map<String, dynamic>> userList = users;
  Set<int> favoriteUsers = {};
  Set<int> blockedUsers = {};

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('User List'),
        centerTitle: true,
        backgroundColor: Colors.blueAccent,
      ),
      body: ListView.separated(
        itemCount: userList.length,
        itemBuilder: (context, index) {
          final user = userList[index];
          final int userId = user["id"];
          final String name = user["fullName"] ?? "Unknown Name";
          final String jobTitle = user["jobTitle"] ?? "Unknown Job";
          final String avatarUrl =
              "https://randomuser.me/api/portraits/men/$userId.jpg";

          bool isFavorite = favoriteUsers.contains(userId);
          bool isBlocked = blockedUsers.contains(userId);

          return Opacity(
            opacity: isBlocked ? 0.5 : 1.0,
            child: ListTile(
              leading: CircleAvatar(
                backgroundImage: NetworkImage(avatarUrl),
                child: avatarUrl.isEmpty ? const Icon(Icons.person) : null,
              ),
              title: Text(
                name,
                style: TextStyle(color: isBlocked ? Colors.grey : Colors.black),
              ),
              subtitle: Text(
                jobTitle,
                style: TextStyle(color: isBlocked ? Colors.grey : Colors.black),
              ),
              trailing: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  if (isFavorite) const Icon(Icons.favorite),
                  PopupMenuButton<String>(
                    onSelected: (String value) {
                      setState(() {
                        if (value == 'Add to favorites') {
                          if (isFavorite) {
                            favoriteUsers.remove(userId);
                          } else {
                            favoriteUsers.add(userId);
                          }
                        } else if (value == 'Block this user') {
                          if (isBlocked) {
                            blockedUsers.remove(userId);
                          } else {
                            blockedUsers.add(userId);
                          }
                        } else if (value == 'Delete this user') {
                          userList.removeAt(index);
                        }
                      });
                    },
                    itemBuilder: (BuildContext context) {
                      return [
                        PopupMenuItem<String>(
                          value: 'Add to favorites',
                          child: Row(
                            children: [
                              Icon(
                                isFavorite
                                    ? Icons.favorite
                                    : Icons.favorite_border,
                              ),
                              const SizedBox(width: 8),
                              Text(
                                isFavorite
                                    ? 'Remove from favorites'
                                    : 'Add to favorites',
                              ),
                            ],
                          ),
                        ),
                        PopupMenuItem<String>(
                          value: 'Block this user',
                          child: Row(
                            children: [
                              Icon(Icons.block),
                              const SizedBox(width: 8),
                              Text(
                                isBlocked ? 'Unblock user' : 'Block this user',
                              ),
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
                ],
              ),
            ),
          );
        },
        separatorBuilder: (context, index) {
          return const Divider();
        },
      ),
    );
  }
}
