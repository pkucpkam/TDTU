import 'dart:async';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';

class NotificationsScreen extends StatefulWidget {
  const NotificationsScreen({super.key});

  @override
  State<NotificationsScreen> createState() => _NotificationsScreenState();
}

class _NotificationsScreenState extends State<NotificationsScreen> {
  final List<Map<String, dynamic>> notifications = [];
  bool isLoading = true;
  StreamSubscription<DatabaseEvent>? _notificationsSubscription;

  @override
  void initState() {
    super.initState();
    _subscribeToNotifications();
  }

  @override
  void dispose() {
    _notificationsSubscription?.cancel();
    super.dispose();
  }

  // Lắng nghe thông báo từ Realtime Database
  void _subscribeToNotifications() {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    if (userId == null) return;

    final notificationsRef = FirebaseDatabase.instance.ref(
      "notifications/$userId",
    );

    _notificationsSubscription = notificationsRef.onValue.listen((event) async {
      if (!event.snapshot.exists) {
        setState(() {
          notifications.clear();
          isLoading = false;
        });
        return;
      }

      final Map data = event.snapshot.value as Map;
      List<Map<String, dynamic>> updatedNotifications = [];

      for (var entry in data.entries) {
        final notificationId = entry.key;
        final notificationData = entry.value as Map;

        final fromUserDoc =
            await FirebaseFirestore.instance
                .collection('users')
                .doc(notificationData['fromId'])
                .get();

        if (fromUserDoc.exists) {
          final userData = fromUserDoc.data() as Map<String, dynamic>;
          updatedNotifications.add({
            'notificationId': notificationId,
            'fromId': notificationData['fromId'],
            'name': userData['name'] ?? 'Unknown',
            'avatar':
                userData['profile_image'] ??
                userData['name']?.substring(0, 1).toUpperCase() ??
                'U',
            'isImage': userData['profile_image'] != null,
            'content': notificationData['content'] ?? '',
            'isRead': notificationData['isRead'] ?? false,
            'timestamp': notificationData['timestamp'] ?? 0,
            'type': notificationData['type'] ?? '',
          });
        }
      }

      updatedNotifications.sort(
        (a, b) => b['timestamp'].compareTo(a['timestamp']),
      );

      setState(() {
        notifications.clear();
        notifications.addAll(updatedNotifications);
        isLoading = false;
      });
    });
  }

  // Đánh dấu thông báo là đã đọc
  Future<void> _markAsRead(String notificationId) async {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    if (userId == null) return;

    try {
      await FirebaseDatabase.instance
          .ref("notifications/$userId/$notificationId")
          .update({'isRead': true});
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Lỗi khi đánh dấu thông báo là đã đọc: $e')),
      );
    }
  }

  // Xóa thông báo
  Future<void> _deleteNotification(String notificationId) async {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    if (userId == null) return;

    try {
      await FirebaseDatabase.instance
          .ref("notifications/$userId/$notificationId")
          .remove();
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Delete notification')));
    } catch (e) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Lỗi khi xóa thông báo: $e')));
    }
  }

  // Chấp nhận yêu cầu kết bạn
  Future<void> _acceptFriendRequest(
    String fromId,
    String notificationId,
  ) async {
    final userId = FirebaseAuth.instance.currentUser?.uid;
    final userDoc =
        await FirebaseFirestore.instance.collection('users').doc(userId).get();
    final userName = userDoc['name'] ?? 'Không xác định';
    if (userId == null) return;

    try {
      await FirebaseDatabase.instance.ref("notifications/$fromId").push().set({
        'fromId': userId,
        'content': '$userName accepted your friend request',
        'isRead': false,
        'timestamp': DateTime.now().millisecondsSinceEpoch,
        'type': 'friend_accepted',
      });
      // Cập nhật danh sách bạn bè của người dùng hiện tại
      await FirebaseFirestore.instance.collection('users').doc(userId).update({
        'friends': FieldValue.arrayUnion([fromId]),
      });

      // Cập nhật danh sách bạn bè của người gửi yêu cầu
      await FirebaseFirestore.instance.collection('users').doc(fromId).update({
        'friends': FieldValue.arrayUnion([userId]),
      });

      // Xóa thông báo sau khi chấp nhận
      await _deleteNotification(notificationId);

      // Thông báo thành công
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Accepted friend request')));

      // Trả về 'notification_updated' để báo hiệu cần cập nhật danh sách bạn bè
      Navigator.pop(context, 'notification_updated');
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error accepting friend request: $e')),
      );
    }
  }

  // Định dạng thời gian
  String _formatTimestamp(int timestamp) {
    if (timestamp == 0) return '';
    final dateTime = DateTime.fromMillisecondsSinceEpoch(timestamp);

    const months = [
      'Th1',
      'Th2',
      'Th3',
      'Th4',
      'Th5',
      'Th6',
      'Th7',
      'Th8',
      'Th9',
      'Th10',
      'Th11',
      'Th12',
    ];

    final month = months[dateTime.month - 1];
    final day = dateTime.day.toString();
    final hour = dateTime.hour.toString().padLeft(2, '0');
    final minute = dateTime.minute.toString().padLeft(2, '0');

    return '$month $day, $hour:$minute';
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context, 'notification_updated');
        return false; // Ngăn chặn pop mặc định
      },
      child: Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          backgroundColor: Colors.white,
          elevation: 0,
          title: const Text(
            'Notifications',
            style: TextStyle(
              color: Colors.black,
              fontWeight: FontWeight.bold,
              fontSize: 24,
            ),
          ),
          leading: IconButton(
            icon: const Icon(Icons.arrow_back, color: Colors.black),
            onPressed: () => Navigator.pop(context, 'notification_updated'),
          ),
        ),
        body:
            isLoading
                ? const Center(child: CircularProgressIndicator())
                : notifications.isEmpty
                ? const Center(
                  child: Text(
                    'Don\'t have any notifications',
                    style: TextStyle(fontSize: 16, color: Colors.grey),
                  ),
                )
                : ListView.builder(
                  padding: const EdgeInsets.symmetric(vertical: 8.0),
                  itemCount: notifications.length,
                  itemBuilder: (context, index) {
                    final notification = notifications[index];
                    return Dismissible(
                      key: Key(notification['notificationId']),
                      direction: DismissDirection.endToStart,
                      background: Container(
                        color: Colors.red,
                        alignment: Alignment.centerRight,
                        padding: const EdgeInsets.only(right: 20.0),
                        child: const Icon(Icons.delete, color: Colors.white),
                      ),
                      confirmDismiss: (direction) async {
                        return await showDialog<bool>(
                          context: context,
                          builder:
                              (context) => AlertDialog(
                                title: const Text('Delete Notification'),
                                content: const Text(
                                  'Do you want to delete this notification?',
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
                                      'Xóa',
                                      style: TextStyle(color: Colors.red),
                                    ),
                                  ),
                                ],
                              ),
                        );
                      },
                      onDismissed: (direction) {
                        _deleteNotification(notification['notificationId']);
                      },
                      child: ListTile(
                        contentPadding: const EdgeInsets.symmetric(
                          horizontal: 16.0,
                          vertical: 8.0,
                        ),
                        leading: CircleAvatar(
                          radius: 24,
                          backgroundImage:
                              notification['isImage']
                                  ? NetworkImage(notification['avatar'])
                                  : null,
                          child:
                              notification['isImage']
                                  ? null
                                  : Text(
                                    notification['avatar'],
                                    style: const TextStyle(fontSize: 18),
                                  ),
                        ),
                        title: Text(
                          notification['name'],
                          style: TextStyle(
                            fontWeight:
                                notification['isRead']
                                    ? FontWeight.normal
                                    : FontWeight.bold,
                          ),
                        ),
                        subtitle: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            Text(
                              notification['content'],
                              style: TextStyle(
                                color:
                                    notification['isRead']
                                        ? Colors.grey
                                        : Colors.black,
                              ),
                            ),
                            if (notification['type'] == 'friend_request')
                              Row(
                                children: [
                                  ElevatedButton(
                                    onPressed: () {
                                      _acceptFriendRequest(
                                        notification['fromId'],
                                        notification['notificationId'],
                                      );
                                    },
                                    child: const Text('Accept'),
                                  ),
                                  const SizedBox(width: 8),
                                  TextButton(
                                    onPressed: () {
                                      _deleteNotification(
                                        notification['notificationId'],
                                      );
                                    },
                                    child: const Text(
                                      'Decline',
                                      style: TextStyle(color: Colors.red),
                                    ),
                                  ),
                                ],
                              ),
                          ],
                        ),
                        trailing: Text(
                          _formatTimestamp(notification['timestamp']),
                          style: const TextStyle(
                            color: Colors.grey,
                            fontSize: 12,
                          ),
                        ),
                        onTap: () {
                          if (!notification['isRead']) {
                            _markAsRead(notification['notificationId']);
                          }
                        },
                      ),
                    );
                  },
                ),
      ),
    );
  }
}
