import 'package:flutter/material.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class InformationScreen extends StatefulWidget {
  const InformationScreen({super.key});

  @override
  _InformationScreenState createState() => _InformationScreenState();
}

class _InformationScreenState extends State<InformationScreen> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _emailController = TextEditingController();
  String? _profileImageUrl;
  String? _userName;
  String? _userEmail;

  @override
  void initState() {
    super.initState();
    _loadUserInfo();
  }

  // Lấy thông tin người dùng từ Firebase
  Future<void> _loadUserInfo() async {
    final user = FirebaseAuth.instance.currentUser;
    if (user != null) {
      DocumentSnapshot userDoc =
          await FirebaseFirestore.instance
              .collection('users')
              .doc(user.uid)
              .get();
      if (userDoc.exists) {
        setState(() {
          _userName = userDoc['name'];
          _userEmail = user.email;
          _profileImageUrl = userDoc['profile_image'];
          _nameController.text = _userName ?? '';
          _emailController.text = _userEmail ?? '';
        });
      }
    }
  }

  // Lưu thông tin người dùng đã thay đổi
  Future<void> _updateUserInfo() async {
    final user = FirebaseAuth.instance.currentUser;
    if (user != null) {
      await FirebaseFirestore.instance.collection('users').doc(user.uid).update(
        {
          'name': _nameController.text,
          // Không cập nhật email trong trường hợp này
        },
      );
    }
    // Hiển thị thông báo thông tin đã được cập nhật
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: const Text(
          'Information has been updated. Changes will be reflected the next time you log in.!',
        ),
        backgroundColor: Colors.green,
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Personal information')),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Đưa avatar ra giữa
            Center(
              child: CircleAvatar(
                radius: 50,
                backgroundImage:
                    _profileImageUrl != null
                        ? NetworkImage(_profileImageUrl!)
                        : const AssetImage('assets/default_avatar.jpg')
                            as ImageProvider,
              ),
            ),
            const SizedBox(height: 20),
            // Trường sửa tên
            TextField(
              controller: _nameController,
              decoration: const InputDecoration(labelText: 'Full Name'),
            ),
            const SizedBox(height: 20),
            // Trường email (không cho sửa)
            TextField(
              controller: _emailController,
              decoration: const InputDecoration(labelText: 'Email'),
              enabled: false, // Disable email field
            ),
            const SizedBox(height: 20),
            // Nút Lưu
            ElevatedButton(
              onPressed: _updateUserInfo,
              child: const Text('Save Changes'),
            ),
          ],
        ),
      ),
    );
  }
}
