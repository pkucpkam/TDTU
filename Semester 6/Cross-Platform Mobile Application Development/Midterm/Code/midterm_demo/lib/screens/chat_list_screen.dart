import 'dart:async';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'package:midterm_demo/screens/chat_screen.dart';
import 'package:midterm_demo/screens/firends_screen.dart';
import 'package:midterm_demo/screens/information.dart';
import 'package:midterm_demo/screens/login_screen.dart';
import 'package:midterm_demo/screens/notifications.dart';

class ChatListScreen extends StatefulWidget {
  const ChatListScreen({super.key});

  @override
  State<ChatListScreen> createState() => _ChatListScreenState();
}

class _ChatListScreenState extends State<ChatListScreen> {
  // Trạng thái loading trang
  bool isLoadingChats = true;
  bool hasUnreadNotifications = false;
  StreamSubscription<DatabaseEvent>? _chatSubscription;
  StreamSubscription<DatabaseEvent>? _notificationsSubscription;
  final Map<String, StreamSubscription<DatabaseEvent>>
  _onlineStatusSubscriptions = {};
  final Map<String, StreamSubscription<DatabaseEvent>> _chatOnlineStatusSubs =
      {};

  // Danh sách người dùng online
  List<Map<String, dynamic>> onlineFriends = [];

  // Danh sách chat
  final List<Map<String, dynamic>> chatList = [];

  String? _userName;
  String? _profileImageUrl;

  @override
  void initState() {
    super.initState();
    _getUserInfo();
    _subscribeToRealtimeUpdates();
    _subscribeToFriendsOnlineStatus();
    _subscribeToNotifications();
  }

  @override
  void dispose() {
    _chatSubscription?.cancel();
    for (var sub in _onlineStatusSubscriptions.values) {
      sub.cancel();
    }
    for (var sub in _chatOnlineStatusSubs.values) {
      sub.cancel();
    }
    _notificationsSubscription?.cancel();
    super.dispose();
  }

  // Lấy thông tin người dùng từ Firebase
  Future<void> _getUserInfo() async {
    User? user = FirebaseAuth.instance.currentUser;
    if (user != null) {
      DocumentSnapshot userDoc =
          await FirebaseFirestore.instance
              .collection('users')
              .doc(user.uid)
              .get();
      if (userDoc.exists) {
        setState(() {
          _userName = userDoc['name'];
          _profileImageUrl = userDoc['profile_image'];
        });
      }
    }
  }

  // Lấy danh sách bạn bè đang online từ Firebase
  void _subscribeToFriendsOnlineStatus() async {
    final user = FirebaseAuth.instance.currentUser;
    if (user == null) return;

    final userDoc =
        await FirebaseFirestore.instance
            .collection('users')
            .doc(user.uid)
            .get();
    final friendIds = userDoc['friends'] ?? [];

    for (final friendId in friendIds) {
      final statusRef = FirebaseDatabase.instance.ref("user_status/$friendId");

      final sub = statusRef.onValue.listen((event) async {
        final isOnline = event.snapshot.value == true;

        final friendDoc =
            await FirebaseFirestore.instance
                .collection('users')
                .doc(friendId)
                .get();
        final friendData = friendDoc.data() as Map<String, dynamic>;

        final index = onlineFriends.indexWhere((f) => f['id'] == friendId);
        final friendInfo = {
          'id': friendId,
          'name': friendData['name'] ?? 'Unknown',
          'avatar':
              friendData['profile_image'] ??
              friendData['name']?.substring(0, 1).toUpperCase() ??
              'U',
          'isImage': friendData['profile_image'] != null,
          'isOnline': isOnline,
        };

        setState(() {
          if (index != -1) {
            onlineFriends[index] = friendInfo;
          } else {
            onlineFriends.add(friendInfo);
          }
        });
      });

      _onlineStatusSubscriptions[friendId] = sub;
    }
  }

  // Load danh sách tin nhắn
  void _subscribeToRealtimeUpdates() {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    if (userId == null) return;

    // Hủy subscription cũ
    _chatSubscription?.cancel();
    for (var sub in _chatOnlineStatusSubs.values) {
      sub.cancel();
    }
    _chatOnlineStatusSubs.clear();

    final chatsRef = FirebaseDatabase.instance.ref("chats");

    _chatSubscription = chatsRef.onValue.listen((event) async {
      if (!event.snapshot.exists) {
        setState(() {
          chatList.clear();
          isLoadingChats = false;
        });
        return;
      }

      final Map data = event.snapshot.value as Map;
      List<Map<String, dynamic>> updatedChats = [];
      List<String> otherUserIds = [];

      // Lấy danh sách chat và user IDs
      for (var entry in data.entries) {
        final chatId = entry.key.toString();
        final chatDetails = entry.value as Map;

        if (chatId.contains(userId) && chatDetails['lastMessage'] != null) {
          final userIds = chatId.split('_');
          final otherUserId = userIds.firstWhere(
            (id) => id != userId,
            orElse: () => '',
          );

          if (otherUserId.isEmpty) continue;

          otherUserIds.add(otherUserId);
          final otherUserDoc =
              await FirebaseFirestore.instance
                  .collection('users')
                  .doc(otherUserId)
                  .get();

          if (otherUserDoc.exists) {
            final userData = otherUserDoc.data() as Map<String, dynamic>;
            updatedChats.add({
              'chatId': chatId,
              'name': userData['name'] ?? 'Unknown',
              'avatar':
                  userData['profile_image'] ??
                  userData['name']?.substring(0, 1).toUpperCase() ??
                  'U',
              'isImage': userData['profile_image'] != null,
              'message': chatDetails['lastMessage']['content'] ?? '',
              'time': _convertTimestampToTime(
                chatDetails['lastMessage']['timestamp'],
              ),
              'isOnline': false, // Sẽ được cập nhật bởi subscription
              'otherUserId': otherUserId,
            });
          }
        }
      }

      // Tạo subscriptions cho trạng thái người dùng
      for (final otherUserId in otherUserIds) {
        if (!_chatOnlineStatusSubs.containsKey(otherUserId)) {
          final statusRef = FirebaseDatabase.instance.ref(
            "user_status/$otherUserId",
          );
          final sub = statusRef.onValue.listen((event) {
            final isOnline = event.snapshot.value == true;
            setState(() {
              final index = chatList.indexWhere(
                (c) => c['otherUserId'] == otherUserId,
              );
              if (index != -1) {
                chatList[index]['isOnline'] = isOnline;
              }
            });
          });
          _chatOnlineStatusSubs[otherUserId] = sub;
        }
      }

      setState(() {
        chatList.clear();
        chatList.addAll(updatedChats);
        isLoadingChats = false;
      });
    });
  }

  // Chuyển đổi timestamp thành thời gian
  String _convertTimestampToTime(dynamic timestamp) {
    if (timestamp == null) return '';
    final dt = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);
    return "${dt.hour.toString().padLeft(2, '0')}:${dt.minute.toString().padLeft(2, '0')}";
  }

  // Hàm xử lý mở hoặc tạo mới cuộc trò chuyện
  Future<void> _startOrOpenChat(String friendId, String friendName) async {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    if (userId == null) return;

    // Kiểm tra xem đã có cuộc trò chuyện với friendId chưa
    final existingChat = chatList.firstWhere(
      (chat) => chat['otherUserId'] == friendId,
      orElse: () => {},
    );

    if (existingChat.isNotEmpty) {
      // Nếu đã có cuộc trò chuyện, mở ChatScreen với chatId hiện có
      Navigator.of(context).push(
        MaterialPageRoute(
          builder:
              (context) => ChatScreen(
                contactName: existingChat['name'],
                chatId: existingChat['chatId'],
                otherUserId: existingChat['otherUserId'],
              ),
        ),
      );
    } else {
      // Nếu chưa có, tạo chatId mới (theo định dạng userId1_userId2, sắp xếp để đảm bảo duy nhất)
      final sortedIds = [userId, friendId]..sort();
      final newChatId = '${sortedIds[0]}_${sortedIds[1]}';

      // Tạo một node chat mới trong Firebase Realtime Database
      await FirebaseDatabase.instance.ref("chats/$newChatId").set({
        'lastMessage': null,
        'participants': {userId: true, friendId: true},
      });

      // Mở ChatScreen với chatId mới
      Navigator.of(context).push(
        MaterialPageRoute(
          builder:
              (context) => ChatScreen(
                contactName: friendName,
                chatId: newChatId,
                otherUserId: friendId,
              ),
        ),
      );
    }
  }

  void _subscribeToNotifications() {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    if (userId == null) return;

    final notificationsRef = FirebaseDatabase.instance.ref(
      "notifications/$userId",
    );

    // Hủy subscription cũ để tránh trùng lặp
    _notificationsSubscription?.cancel();

    // Lắng nghe sự kiện onChildAdded để phát hiện thông báo mới
    _notificationsSubscription = notificationsRef.onChildAdded.listen((event) {
      final notificationData = event.snapshot.value as Map<dynamic, dynamic>?;
      if (notificationData != null && notificationData['isRead'] == false) {
        setState(() {
          hasUnreadNotifications = true; // Có thông báo mới chưa đọc
        });
      }
    });

    // Lắng nghe sự kiện onValue để cập nhật trạng thái tổng thể
    notificationsRef.onValue.listen((event) {
      bool hasUnread = false;

      if (event.snapshot.exists) {
        final Map data = event.snapshot.value as Map;
        for (var entry in data.entries) {
          final notificationData = entry.value as Map;
          if (notificationData['isRead'] == false) {
            hasUnread = true; // Có ít nhất một thông báo chưa đọc
            break;
          }
        }
      }

      setState(() {
        hasUnreadNotifications = hasUnread; // Cập nhật trạng thái dấu đỏ
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: const Text(
          'Chats',
          style: TextStyle(
            color: Colors.black,
            fontWeight: FontWeight.bold,
            fontSize: 24,
          ),
        ),
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: const Icon(Icons.menu, color: Colors.black),
              onPressed: () {
                Scaffold.of(context).openDrawer();
              },
            );
          },
        ),
        actions: [
          IconButton(
            icon: const Icon(Icons.camera_alt, color: Colors.black),
            onPressed: () {},
          ),
          IconButton(
            icon: const Icon(Icons.edit, color: Colors.black),
            onPressed: () {},
          ),
        ],
      ),
      drawer: Drawer(
        child: Column(
          children: [
            UserAccountsDrawerHeader(
              accountName: Text(_userName ?? 'Loading...'),
              accountEmail: Text(
                FirebaseAuth.instance.currentUser?.email ?? 'No email',
              ),
              decoration: const BoxDecoration(color: Colors.blue),
              currentAccountPicture: CircleAvatar(
                radius: 50,
                backgroundImage:
                    _profileImageUrl != null
                        ? NetworkImage(_profileImageUrl!)
                        : const AssetImage('assets/default_avatar.jpg')
                            as ImageProvider,
              ),
            ),
            ListTile(
              title: const Text('Settings'),
              leading: const Icon(Icons.settings),
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const InformationScreen(),
                  ),
                );
              },
            ),
            ListTile(
              title: const Text('Log out'),
              leading: const Icon(Icons.exit_to_app),
              onTap: () async {
                final currentUser = FirebaseAuth.instance.currentUser;
                if (currentUser != null) {
                  await FirebaseDatabase.instance
                      .ref("user_status/${currentUser.uid}")
                      .set(false);
                  await FirebaseAuth.instance.signOut();
                }
                Navigator.pushReplacement(
                  context,
                  MaterialPageRoute(builder: (context) => LoginScreen()),
                );
              },
            ),
          ],
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Ô tìm kiếm
            Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 15.0,
                vertical: 8.0,
              ),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.grey.shade200,
                  borderRadius: BorderRadius.circular(20),
                ),
                child: const TextField(
                  decoration: InputDecoration(
                    hintText: 'Search',
                    prefixIcon: Icon(Icons.search, color: Colors.grey),
                    border: InputBorder.none,
                    contentPadding: EdgeInsets.symmetric(vertical: 12.0),
                  ),
                ),
              ),
            ),
            // Danh sách bạn bè đang online
            Container(
              height: 110,
              padding: const EdgeInsets.symmetric(vertical: 10),
              child:
                  onlineFriends.isEmpty
                      ? const Center(child: Text('No friends online'))
                      : ListView.builder(
                        scrollDirection: Axis.horizontal,
                        padding: const EdgeInsets.symmetric(horizontal: 10.0),
                        itemCount: onlineFriends.length,
                        itemBuilder: (context, index) {
                          final friend = onlineFriends[index];
                          return Padding(
                            padding: const EdgeInsets.symmetric(
                              horizontal: 8.0,
                            ),
                            child: Column(
                              children: [
                                GestureDetector(
                                  onTap: () {
                                    _startOrOpenChat(
                                      friend['id'],
                                      friend['name'],
                                    );
                                  },
                                  child: Stack(
                                    children: [
                                      CircleAvatar(
                                        radius: 30,
                                        backgroundImage:
                                            friend['isImage']
                                                ? NetworkImage(friend['avatar'])
                                                : null,
                                        child:
                                            friend['isImage']
                                                ? null
                                                : Text(
                                                  friend['avatar'],
                                                  style: const TextStyle(
                                                    fontSize: 20,
                                                  ),
                                                ),
                                      ),
                                      Positioned(
                                        right: 0,
                                        bottom: 0,
                                        child: Container(
                                          width: 15,
                                          height: 15,
                                          decoration: BoxDecoration(
                                            color:
                                                friend['isOnline'] == true
                                                    ? Colors.green
                                                    : Colors.grey,
                                            shape: BoxShape.circle,
                                            border: Border.all(
                                              color: Colors.white,
                                              width: 2,
                                            ),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                                const SizedBox(height: 6),
                                Text(
                                  friend['name'].toString().split(' ').last,
                                  style: const TextStyle(
                                    fontSize: 12,
                                    fontWeight: FontWeight.w500,
                                  ),
                                ),
                              ],
                            ),
                          );
                        },
                      ),
            ),
            const Divider(height: 1),
            // Danh sách chat
            isLoadingChats
                ? const Padding(
                  padding: EdgeInsets.symmetric(vertical: 20),
                  child: CircularProgressIndicator(),
                )
                : chatList.isEmpty
                ? const Padding(
                  padding: EdgeInsets.symmetric(vertical: 20),
                  child: Text(
                    'No chat now',
                    style: TextStyle(fontSize: 16, color: Colors.grey),
                  ),
                )
                : ListView.builder(
                  shrinkWrap: true,
                  physics: const NeverScrollableScrollPhysics(),
                  itemCount: chatList.length,
                  itemBuilder: (context, index) {
                    final chat = chatList[index];
                    return Dismissible(
                      key: Key(chat['chatId']),
                      direction: DismissDirection.endToStart,
                      confirmDismiss: (direction) async {
                        return await showDialog<bool>(
                          context: context,
                          builder:
                              (context) => AlertDialog(
                                title: const Text('Delete chat'),
                                content: Text(
                                  'Are you sure you want to delete the conversation with ${chat['name']}? This action cannot be undone.',
                                ),
                                actions: [
                                  TextButton(
                                    onPressed:
                                        () => Navigator.of(context).pop(false),
                                    child: const Text('Cancel'),
                                  ),
                                  TextButton(
                                    onPressed:
                                        () => Navigator.of(context).pop(true),
                                    child: const Text(
                                      'Delete',
                                      style: TextStyle(color: Colors.red),
                                    ),
                                  ),
                                ],
                              ),
                        );
                      },
                      onDismissed: (direction) {
                        try {
                          FirebaseDatabase.instance
                              .ref("chats/${chat['chatId']}")
                              .remove();
                          setState(() {
                            chatList.removeAt(index);
                          });
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text('Conversation has been deleted'),
                            ),
                          );
                        } catch (e) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text(
                                'Error while deleting conversation: $e',
                              ),
                            ),
                          );
                        }
                      },
                      background: Container(
                        color: Colors.red,
                        alignment: Alignment.centerRight,
                        padding: const EdgeInsets.only(right: 20.0),
                        child: const Icon(Icons.delete, color: Colors.white),
                      ),
                      child: ListTile(
                        contentPadding: const EdgeInsets.symmetric(
                          horizontal: 16.0,
                          vertical: 6.0,
                        ),
                        leading: Stack(
                          children: [
                            CircleAvatar(
                              radius: 28,
                              backgroundImage:
                                  chat['isImage']
                                      ? NetworkImage(chat['avatar'])
                                      : null,
                              child:
                                  chat['isImage']
                                      ? null
                                      : Text(
                                        chat['avatar'],
                                        style: const TextStyle(fontSize: 20),
                                      ),
                            ),
                            
                          ],
                        ),
                        title: Text(
                          chat['name'],
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 16,
                          ),
                        ),
                        subtitle: Text(
                          chat['message'],
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                          style: const TextStyle(color: Colors.grey),
                        ),
                        trailing: Text(
                          chat['time'],
                          style: const TextStyle(
                            color: Colors.grey,
                            fontSize: 12,
                          ),
                        ),
                        onTap: () {
                          Navigator.of(context).push(
                            MaterialPageRoute(
                              builder:
                                  (context) => ChatScreen(
                                    contactName: chat['name'],
                                    chatId: chat['chatId'],
                                    otherUserId: chat['otherUserId'],
                                  ),
                            ),
                          );
                        },
                      ),
                    );
                  },
                ),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        selectedItemColor: Colors.blue,
        unselectedItemColor: Colors.grey,
        currentIndex: 0,
        onTap: (index) async {
          if (index == 2) {
            final result = await Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => const FriendsScreen()),
            );

            if (result == 'friends_updated') {
              // Xóa và tải lại danh sách bạn bè trực tuyến
              setState(() {
                onlineFriends.clear();
              });
              // Hủy các subscription hiện tại để tránh trùng lặp
              for (var sub in _onlineStatusSubscriptions.values) {
                sub.cancel();
              }
              _onlineStatusSubscriptions.clear();
              _subscribeToFriendsOnlineStatus();
            }
          } else if (index == 3) {
            final result = await Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => const NotificationsScreen(),
              ),
            );
            if (result == 'notification_updated') {
              // Xóa và tải lại danh sách bạn bè trực tuyến
              setState(() {
                onlineFriends.clear();
              });
              // Hủy các subscription hiện tại để tránh trùng lặp
              for (var sub in _onlineStatusSubscriptions.values) {
                sub.cancel();
              }
              _onlineStatusSubscriptions.clear();
              _subscribeToFriendsOnlineStatus();
            }
          }
        },

        items: [
          const BottomNavigationBarItem(
            icon: Icon(Icons.chat_bubble),
            label: 'Chats',
          ),
          const BottomNavigationBarItem(
            icon: Icon(Icons.phone),
            label: 'Calls',
          ),
          const BottomNavigationBarItem(
            icon: Icon(Icons.people),
            label: 'People',
          ),
          BottomNavigationBarItem(
            icon: Stack(
              clipBehavior: Clip.none,
              children: [
                const Icon(Icons.notification_add),
                if (hasUnreadNotifications) // Chỉ hiển thị chấm đỏ nếu có thông báo chưa đọc
                  Positioned(
                    right: -2,
                    top: -2,
                    child: Container(
                      width: 10, // Kích thước chấm đỏ
                      height: 10,
                      decoration: BoxDecoration(
                        color: Colors.red,
                        shape: BoxShape.circle,
                      ),
                    ),
                  ),
              ],
            ),
            label: 'Notifications',
          ),
        ],
      ),
    );
  }
}
