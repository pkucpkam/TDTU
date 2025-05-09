import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';
import 'package:cloud_functions/cloud_functions.dart';

class ChatScreen extends StatefulWidget {
  final String? contactName;
  final String? chatId;
  final String? otherUserId;

  const ChatScreen({
    super.key,
    this.contactName,
    this.chatId,
    this.otherUserId,
  });

  @override
  _ChatScreenState createState() => _ChatScreenState();
}

class _ChatScreenState extends State<ChatScreen> {
  final _messageController = TextEditingController();
  List<Map<String, dynamic>> messages = [];
  bool isOnline = true;

  late DatabaseReference _chatRef;
  final currentUser = FirebaseAuth.instance.currentUser;

  Future<void> _sendMessageViaCloudFunction(String messageText) async {
    try {
      final callable = FirebaseFunctions.instance.httpsCallable(
        'filterAndSendMessage',
      );

      await callable.call({
        'chatId': widget.chatId,
        'senderId': currentUser?.uid,
        'content': messageText,
      });
      // ignore: empty_catches
    } catch (e) {}
  }

  @override
  void initState() {
    super.initState();

    _chatRef = FirebaseDatabase.instance.ref("chats/${widget.chatId}/messages");

    if (widget.otherUserId != null) {
      FirebaseDatabase.instance
          .ref("user_status/${widget.otherUserId}")
          .onValue
          .listen((event) {
            final value = event.snapshot.value;
            setState(() {
              isOnline = value == true;
            });
          });
    }

    _chatRef.orderByChild('timestamp').onValue.listen((event) {
      if (event.snapshot.exists) {
        final data = event.snapshot.value as Map<dynamic, dynamic>;
        final List<Map<String, dynamic>> loadedMessages = [];

        data.forEach((key, value) {
          loadedMessages.add({
            'text': value['content'],
            'time': _formatTime(value['timestamp']),
            'timestamp': value['timestamp'],
            'isMe': value['senderId'] == currentUser?.uid,
            'edited': value['edited'] ?? false,
          });
        });

        loadedMessages.sort((a, b) => a['timestamp'].compareTo(b['timestamp']));

        setState(() {
          messages = loadedMessages;
        });
      }
    });
  }

  void _sendMessage({String? text}) async {
    final messageText = text ?? _messageController.text.trim();
    if (messageText.isEmpty || widget.chatId == null || currentUser == null) {
      return;
    }

    await _sendMessageViaCloudFunction(messageText);
    _messageController.clear();
  }

  String _formatTime(int timestamp) {
    final dt = DateTime.fromMillisecondsSinceEpoch(timestamp * 1000);
    return "${dt.hour.toString().padLeft(2, '0')}:${dt.minute.toString().padLeft(2, '0')}";
  }

  void _showMessageOptions(BuildContext context, int index) {
    showModalBottomSheet(
      context: context,
      builder: (context) {
        return SafeArea(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              ListTile(
                leading: const Icon(Icons.delete),
                title: const Text('Delete message'),
                onTap: () {
                  _deleteMessage(index);
                  Navigator.pop(context);
                },
              ),
              ListTile(
                leading: const Icon(Icons.edit),
                title: const Text('Edit message'),
                onTap: () {
                  Navigator.pop(context);
                  _editMessage(index);
                },
              ),
            ],
          ),
        );
      },
    );
  }

  Future<void> _deleteMessage(int index) async {
    try {
      // Lấy danh sách tin nhắn từ Firebase để tìm key của tin nhắn cần xóa
      final snapshot = await _chatRef.orderByChild('timestamp').once();
      if (snapshot.snapshot.exists) {
        final data = snapshot.snapshot.value as Map<dynamic, dynamic>;
        String? messageKey;

        // Tìm key của tin nhắn dựa trên timestamp và nội dung
        data.forEach((key, value) {
          if (value['timestamp'] == messages[index]['timestamp'] &&
              value['content'] == messages[index]['text']) {
            messageKey = key;
          }
        });

        if (messageKey != null) {
          // Xóa tin nhắn
          await _chatRef.child(messageKey!).remove();

          // Cập nhật lastMessage
          await _updateLastMessage();
        }
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error while deleting message: $e')),
      );
    }
  }

  //
  void _editMessage(int index) {
    final message = messages[index];
    final messageController = TextEditingController(text: message['text']);

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Edit Message'),
          content: TextField(
            controller: messageController,
            decoration: const InputDecoration(hintText: 'Enter new message'),
          ),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                final newText = messageController.text.trim();
                if (newText.isNotEmpty) {
                  await _updateMessage(index, newText);
                  Navigator.pop(context);
                }
              },
              child: const Text('Save'),
            ),
          ],
        );
      },
    );
  }

  Future<void> _updateMessage(int index, String newText) async {
    if (newText.trim().isEmpty) return;

    try {
      // Lấy danh sách tin nhắn từ Firebase để tìm key của tin nhắn cần sửa
      final snapshot = await _chatRef.orderByChild('timestamp').once();
      if (snapshot.snapshot.exists) {
        final data = snapshot.snapshot.value as Map<dynamic, dynamic>;
        String? messageKey;

        // Tìm key của tin nhắn dựa trên timestamp và nội dung
        data.forEach((key, value) {
          if (value['timestamp'] == messages[index]['timestamp'] &&
              value['content'] == messages[index]['text']) {
            messageKey = key;
          }
        });

        if (messageKey != null) {
          // Cập nhật tin nhắn, giữ nguyên timestamp
          await _chatRef.child(messageKey!).update({
            'content': newText,
            'senderId': currentUser?.uid, // Giữ nguyên senderId
            'edited': true,
          });

          // Kiểm tra xem tin nhắn sửa có phải là tin nhắn cuối cùng không
          final latestMessage = messages.last;
          if (messages[index]['timestamp'] == latestMessage['timestamp'] &&
              messages[index]['text'] == latestMessage['text']) {
            // Chỉ cập nhật lastMessage nếu tin nhắn sửa là tin nhắn cuối
            await _updateLastMessage();
          }
        } else {
          throw Exception('No message found to edit');
        }
      } else {
        throw Exception('No messages found in chat');
      }
    } catch (e) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Error while editing message: $e')));
    }
  }

  Future<void> _updateLastMessage() async {
    try {
      // Lấy tất cả tin nhắn còn lại, sắp xếp theo timestamp
      final snapshot = await _chatRef.orderByChild('timestamp').once();
      final lastMessageRef = FirebaseDatabase.instance.ref(
        "chats/${widget.chatId}/lastMessage",
      );

      if (snapshot.snapshot.exists) {
        final data = snapshot.snapshot.value as Map<dynamic, dynamic>;
        // Chuyển dữ liệu thành danh sách và sắp xếp để lấy tin nhắn mới nhất
        final List<Map<dynamic, dynamic>> messageList = [];
        data.forEach((key, value) {
          messageList.add(value);
        });

        // Sắp xếp theo timestamp giảm dần
        messageList.sort((a, b) => b['timestamp'].compareTo(a['timestamp']));

        // Lấy tin nhắn mới nhất
        final latestMessage = messageList.first;

        // Cập nhật lastMessage
        await lastMessageRef.set({
          'content': latestMessage['content'],
          'timestamp': latestMessage['timestamp'],
          'senderId': latestMessage['senderId'],
        });
      } else {
        // Nếu không còn tin nhắn, xóa node lastMessage
        await lastMessageRef.remove();
      }
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Lỗi khi cập nhật last message: $e')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(widget.contactName ?? 'Chat'),
            Text(
              isOnline ? 'Online' : 'Offline',
              style: TextStyle(
                fontSize: 12,
                color: isOnline ? Colors.green : Colors.grey,
              ),
            ),
          ],
        ),
        actions: [
          IconButton(
            icon: Icon(Icons.logout),
            onPressed: () => Navigator.pop(context),
          ),
        ],
      ),
      body: Column(
        children: [
          Expanded(
            child: ListView.builder(
              itemCount: messages.length,
              itemBuilder: (context, index) {
                final message = messages[index];
                final bool isEdited = message['edited'] ?? false;

                return GestureDetector(
                  onLongPress:
                      message['isMe']
                          ? () => _showMessageOptions(context, index)
                          : null, // Chỉ cho phép xóa/sửa tin nhắn của người dùng hiện tại
                  child: Align(
                    alignment:
                        message['isMe']
                            ? Alignment.centerRight
                            : Alignment.centerLeft,
                    child: Container(
                      margin: const EdgeInsets.symmetric(
                        vertical: 4,
                        horizontal: 8,
                      ),
                      padding: const EdgeInsets.all(12),
                      decoration: BoxDecoration(
                        color:
                            message['isMe']
                                ? Colors.blue[100]
                                : Colors.grey[300],
                        borderRadius: BorderRadius.circular(16),
                      ),
                      child: Column(
                        crossAxisAlignment:
                            message['isMe']
                                ? CrossAxisAlignment.end
                                : CrossAxisAlignment.start,
                        children: [
                          Text(message['text']),
                          const SizedBox(height: 4),
                          Row(
                            mainAxisSize: MainAxisSize.min,
                            children: [
                              Text(
                                message['time'],
                                style: const TextStyle(
                                  fontSize: 10,
                                  color: Colors.grey,
                                ),
                              ),
                              if (isEdited) 
                                const Padding(
                                  padding: EdgeInsets.only(left: 6),
                                  child: Text(
                                    '(edited)',
                                    style: TextStyle(
                                      fontSize: 10,
                                      fontStyle: FontStyle.italic,
                                      color: Colors.black54,
                                    ),
                                  ),
                                ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                );
              },
            ),
          ),

          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              children: [
                IconButton(
                  icon: const Icon(Icons.image, color: Colors.blue),
                  onPressed: () {
                    // Tích hợp gửi ảnh sau
                  },
                ),
                Expanded(
                  child: Container(
                    padding: const EdgeInsets.symmetric(horizontal: 12),
                    decoration: BoxDecoration(
                      color: Colors.grey.shade200,
                      borderRadius: BorderRadius.circular(30),
                    ),
                    child: TextField(
                      controller: _messageController,
                      decoration: const InputDecoration(
                        hintText: 'Enter a message ...',
                        border: InputBorder.none,
                      ),
                    ),
                  ),
                ),
                const SizedBox(width: 4),
                GestureDetector(
                  onTap: _sendMessage,
                  child: Container(
                    padding: const EdgeInsets.all(10),
                    decoration: const BoxDecoration(
                      shape: BoxShape.circle,
                      color: Colors.blue,
                    ),
                    child: const Icon(Icons.send, color: Colors.white),
                  ),
                ),
                const SizedBox(width: 4),
                GestureDetector(
                  onTap: () => _sendMessage(text: '❤️'),
                  child: Container(
                    padding: const EdgeInsets.all(10),
                    decoration: const BoxDecoration(
                      shape: BoxShape.circle,
                      color: Colors.pink,
                    ),
                    child: const Icon(Icons.favorite, color: Colors.white),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}
