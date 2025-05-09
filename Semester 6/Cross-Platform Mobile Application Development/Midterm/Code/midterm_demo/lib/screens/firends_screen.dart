import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:firebase_database/firebase_database.dart';
import 'package:flutter/material.dart';

class FriendsScreen extends StatefulWidget {
  const FriendsScreen({super.key});

  @override
  State<FriendsScreen> createState() => _FriendsScreenState();
}

class _FriendsScreenState extends State<FriendsScreen> {
  final TextEditingController _searchController = TextEditingController();
  List<Map<String, dynamic>> searchResults = [];
  List<Map<String, dynamic>> friendsList = [];
  bool isSearching = false;
  bool isLoadingFriends = true;

  @override
  void initState() {
    super.initState();
    _loadFriends();
    _searchController.addListener(_onSearchChanged);
  }

  @override
  void dispose() {
    _searchController.dispose();
    super.dispose();
  }

  // Tải danh sách bạn bè
  Future<void> _loadFriends() async {
    final user = FirebaseAuth.instance.currentUser;
    if (user == null) return;

    try {
      final userDoc =
          await FirebaseFirestore.instance
              .collection('users')
              .doc(user.uid)
              .get();
      final friendIds = userDoc['friends'] ?? [];

      List<Map<String, dynamic>> friends = [];
      for (final friendId in friendIds) {
        final friendDoc =
            await FirebaseFirestore.instance
                .collection('users')
                .doc(friendId)
                .get();
        if (friendDoc.exists) {
          final friendData = friendDoc.data() as Map<String, dynamic>;
          friends.add({
            'id': friendId,
            'name': friendData['name'] ?? 'Không xác định',
            'avatar':
                friendData['profile_image'] ??
                friendData['name']?.substring(0, 1).toUpperCase() ??
                'U',
            'isImage': friendData['profile_image'] != null,
          });
        }
      }

      setState(() {
        friendsList = friends;
        isLoadingFriends = false;
      });
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error loaded friend request: $e')),
      );
      setState(() {
        isLoadingFriends = false;
      });
    }
  }

  // Xử lý thay đổi trong ô tìm kiếm
  void _onSearchChanged() async {
    final query = _searchController.text.trim();
    if (query.isEmpty) {
      setState(() {
        searchResults = [];
        isSearching = false;
      });
      return;
    }

    setState(() {
      isSearching = true;
    });

    final currentUser = FirebaseAuth.instance.currentUser;
    try {
      final snapshot =
          await FirebaseFirestore.instance
              .collection('users')
              .where('name', isGreaterThanOrEqualTo: query)
              .where('name', isLessThanOrEqualTo: '$query\uf8ff')
              .get();

      List<Map<String, dynamic>> results = [];
      for (var doc in snapshot.docs) {
        if (doc.id != currentUser?.uid) {
          final userData = doc.data();
          // Kiểm tra xem đã là bạn bè chưa
          bool isFriend = friendsList.any((friend) => friend['id'] == doc.id);
          // Kiểm tra xem đã gửi yêu cầu kết bạn chưa
          bool isRequestPending = false;
          if (!isFriend) {
            final requestSnapshot =
                await FirebaseFirestore.instance
                    .collection('friend_requests')
                    .where('from', isEqualTo: currentUser?.uid)
                    .where('to', isEqualTo: doc.id)
                    .where('status', isEqualTo: 'pending')
                    .get();
            isRequestPending = requestSnapshot.docs.isNotEmpty;
          }

          results.add({
            'id': doc.id,
            'name': userData['name'] ?? 'Không xác định',
            'avatar':
                userData['profile_image'] ??
                userData['name']?.substring(0, 1).toUpperCase() ??
                'U',
            'isImage': userData['profile_image'] != null,
            'isFriend': isFriend,
            'isRequestPending': isRequestPending,
          });
        }
      }

      setState(() {
        searchResults = results;
        isSearching = false;
      });
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Error find friend request: $e')),
      );
      setState(() {
        searchResults = [];
        isSearching = false;
      });
    }
  }

  // Gửi yêu cầu kết bạn
  Future<void> _sendFriendRequest(String toUserId) async {
    final user = FirebaseAuth.instance.currentUser;
    final userDoc =
        await FirebaseFirestore.instance
            .collection('users')
            .doc(user?.uid)
            .get();
    final userName = userDoc['name'] ?? 'Không xác định';
    if (user == null) return;

    try {
      await FirebaseFirestore.instance.collection('friend_requests').add({
        'from': user.uid,
        'to': toUserId,
        'status': 'pending',
        'timestamp': FieldValue.serverTimestamp(),
      });

      await FirebaseDatabase.instance
          .ref("notifications/$toUserId")
          .push()
          .set({
            'fromId': user.uid,
            'content': '$userName has sent you a friend request',
            'isRead': false,
            'timestamp': DateTime.now().millisecondsSinceEpoch,
            'type': 'friend_request',
          });

      setState(() {
        final index = searchResults.indexWhere(
          (user) => user['id'] == toUserId,
        );
        if (index != -1) {
          searchResults[index]['isRequestPending'] = true;
        }
      });

      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Sent friend request')));
    } catch (e) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Error: $e')));
    }
  }

  // Xóa bạn bè
  Future<void> _removeFriend(String friendId) async {
    final user = FirebaseAuth.instance.currentUser;
    if (user == null) return;

    try {
      await FirebaseFirestore.instance.collection('users').doc(user.uid).update(
        {
          'friends': FieldValue.arrayRemove([friendId]),
        },
      );

      await FirebaseFirestore.instance.collection('users').doc(friendId).update(
        {
          'friends': FieldValue.arrayRemove([user.uid]),
        },
      );

      await _loadFriends();
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(const SnackBar(content: Text('Deleted friend')));
    } catch (e) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Error to delete: $e')));
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        backgroundColor: Colors.white,
        elevation: 0,
        title: const Text(
          'Friends',
          style: TextStyle(
            color: Colors.black,
            fontWeight: FontWeight.bold,
            fontSize: 24,
          ),
        ),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back, color: Colors.black),
          onPressed: () => Navigator.pop(context, 'friends_updated'),
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
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
                child: TextField(
                  controller: _searchController,
                  decoration: const InputDecoration(
                    hintText: 'Search friends',
                    prefixIcon: Icon(Icons.search, color: Colors.grey),
                    border: InputBorder.none,
                    contentPadding: EdgeInsets.symmetric(vertical: 12.0),
                  ),
                ),
              ),
            ),
            // Kết quả tìm kiếm
            if (searchResults.isNotEmpty || isSearching)
              Padding(
                padding: const EdgeInsets.symmetric(
                  horizontal: 15.0,
                  vertical: 8.0,
                ),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const Text(
                      'Search Results',
                      style: TextStyle(
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 8),
                    isSearching
                        ? const Center(child: CircularProgressIndicator())
                        : ListView.builder(
                          shrinkWrap: true,
                          physics: const NeverScrollableScrollPhysics(),
                          itemCount: searchResults.length,
                          itemBuilder: (context, index) {
                            final user = searchResults[index];
                            return ListTile(
                              leading: CircleAvatar(
                                radius: 20,
                                backgroundImage:
                                    user['isImage']
                                        ? NetworkImage(user['avatar'])
                                        : null,
                                child:
                                    user['isImage']
                                        ? null
                                        : Text(
                                          user['avatar'],
                                          style: const TextStyle(fontSize: 16),
                                        ),
                              ),
                              title: Text(user['name']),
                              trailing:
                                  user['isFriend']
                                      ? null
                                      : ElevatedButton(
                                        onPressed:
                                            user['isRequestPending']
                                                ? null
                                                : () => _sendFriendRequest(
                                                  user['id'],
                                                ),
                                        style: ElevatedButton.styleFrom(
                                          backgroundColor:
                                              user['isRequestPending']
                                                  ? Colors.grey
                                                  : Colors.blue,
                                          shape: RoundedRectangleBorder(
                                            borderRadius: BorderRadius.circular(
                                              20,
                                            ),
                                          ),
                                        ),
                                        child: Text(
                                          user['isRequestPending']
                                              ? 'Friend request sent'
                                              : 'Add friend',
                                          style: const TextStyle(
                                            color: Colors.white,
                                          ),
                                        ),
                                      ),
                            );
                          },
                        ),
                  ],
                ),
              ),
            // Danh sách bạn bè
            Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 15.0,
                vertical: 8.0,
              ),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    'List of Friends',
                    style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(height: 8),
                  isLoadingFriends
                      ? const Center(child: CircularProgressIndicator())
                      : friendsList.isEmpty
                      ? const Center(child: Text('Bạn chưa có bạn bè nào'))
                      : ListView.builder(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemCount: friendsList.length,
                        itemBuilder: (context, index) {
                          final friend = friendsList[index];
                          return Dismissible(
                            key: Key(friend['id']),
                            direction: DismissDirection.endToStart,
                            background: Container(
                              alignment: Alignment.centerRight,
                              padding: const EdgeInsets.symmetric(
                                horizontal: 20,
                              ),
                              color: Colors.red,
                              child: const Icon(
                                Icons.delete,
                                color: Colors.white,
                              ),
                            ),
                            confirmDismiss: (_) async {
                              return await showDialog(
                                context: context,
                                builder:
                                    (context) => AlertDialog(
                                      title: const Text('Xác nhận'),
                                      content: Text(
                                        'Bạn có chắc muốn xóa ${friend['name']} khỏi danh sách bạn bè không?',
                                      ),
                                      actions: [
                                        TextButton(
                                          onPressed:
                                              () => Navigator.of(
                                                context,
                                              ).pop(false),
                                          child: const Text('Hủy'),
                                        ),
                                        TextButton(
                                          onPressed:
                                              () => Navigator.of(
                                                context,
                                              ).pop(true),
                                          child: const Text(
                                            'Xóa',
                                            style: TextStyle(color: Colors.red),
                                          ),
                                        ),
                                      ],
                                    ),
                              );
                            },
                            onDismissed: (_) => _removeFriend(friend['id']),
                            child: ListTile(
                              leading: CircleAvatar(
                                radius: 20,
                                backgroundImage:
                                    friend['isImage']
                                        ? NetworkImage(friend['avatar'])
                                        : null,
                                child:
                                    friend['isImage']
                                        ? null
                                        : Text(
                                          friend['avatar'],
                                          style: const TextStyle(fontSize: 16),
                                        ),
                              ),
                              title: Text(friend['name']),
                            ),
                          );
                        },
                      ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
