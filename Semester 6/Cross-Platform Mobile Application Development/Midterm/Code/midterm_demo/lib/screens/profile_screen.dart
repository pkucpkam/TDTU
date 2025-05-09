import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'dart:io';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'login_screen.dart'; // Import LoginScreen

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({super.key});

  @override
  _ProfileScreenState createState() => _ProfileScreenState();
}

class _ProfileScreenState extends State<ProfileScreen> {
  File? _profileImage;
  final ImagePicker _picker = ImagePicker();
  bool _isLoading = false; // Biến trạng thái để theo dõi việc tải lên

  // Hàm chọn ảnh từ thư viện hoặc chụp ảnh
  Future<void> _pickImage() async {
    final XFile? pickedFile = await _picker.pickImage(
      source: ImageSource.gallery,
    ); // Hoặc ImageSource.camera cho camera
    if (pickedFile != null) {
      setState(() {
        _profileImage = File(pickedFile.path);
      });
    }
  }

  // Hàm lưu thông tin người dùng vào Firebase Storage và Firestore
  Future<void> _saveProfile() async {
    if (_profileImage == null) {
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('Vui lòng tải hình ảnh.')));
      return;
    }

    User? user = FirebaseAuth.instance.currentUser;
  if (user == null) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text('Không tìm thấy người dùng. Vui lòng đăng nhập lại.')),
    );
    Navigator.pushReplacement(
      context,
      MaterialPageRoute(builder: (context) => LoginScreen()),
    );
    return;
  }

    setState(() {
      _isLoading = true; // Bật loading spinner
    });

    try {
      // Lưu ảnh lên Firebase Storage
      String fileName = DateTime.now().millisecondsSinceEpoch.toString();
      Reference storageRef = FirebaseStorage.instance.ref().child(
        'profile_images/$fileName.jpg',
      );

      UploadTask uploadTask = storageRef.putFile(_profileImage!);
      TaskSnapshot taskSnapshot = await uploadTask.whenComplete(() {});

      // Lấy URL tải về của ảnh
      String imageUrl = await taskSnapshot.ref.getDownloadURL();

      String uid =
          FirebaseAuth
              .instance
              .currentUser!
              .uid; // Lấy UID của người dùng hiện tại

      // Lưu thông tin người dùng vào Firestore
      FirebaseFirestore.instance.collection('users').doc(uid).update({
        'profile_image': imageUrl,
      });

      // Hiển thị thông báo thành công
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(
          content: Text('Account registration successful!'),
          backgroundColor: Colors.green,
          duration: Duration(seconds: 2),
        ),
      );

      // Chuyển về màn hình LoginScreen sau khi lưu thành công
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => LoginScreen()),
      );
    } catch (e) {
      // Nếu có lỗi
      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('An error has occurred: $e')));
    } finally {
      setState(() {
        _isLoading = false; // Tắt loading spinner
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Personal Information'),
        backgroundColor: Color(0xFF039BE5), // Màu xanh dương nhạt
      ),
      body: Stack(
        children: [
          // Nội dung chính của màn hình
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment:
                    CrossAxisAlignment.center, // Căn giữa các phần tử
                mainAxisAlignment:
                    MainAxisAlignment.center, // Căn giữa trên trục dọc
                children: [
                  // Hướng dẫn tải ảnh đại diện
                  Align(
                    // Đảm bảo văn bản này căn trái hợp lý
                    alignment: Alignment.center,
                    child: Text(
                      'Upload a profile picture to complete your profile.',
                      style: TextStyle(fontSize: 16, color: Colors.grey[700]),
                    ),
                  ),
                  SizedBox(height: 16),

                  // Ảnh đại diện
                  GestureDetector(
                    onTap: _pickImage,
                    child: CircleAvatar(
                      radius: 80,
                      backgroundColor: Colors.grey.shade300,
                      backgroundImage:
                          _profileImage != null
                              ? FileImage(_profileImage!)
                              : null,
                      child:
                          _profileImage == null
                              ? Icon(
                                Icons.camera_alt,
                                size: 50,
                                color: Colors.white,
                              )
                              : null,
                    ),
                  ),
                  SizedBox(height: 24),

                  // Nút lưu thông tin
                  ElevatedButton(
                    onPressed: _saveProfile,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Color(0xFF039BE5),
                      padding: EdgeInsets.symmetric(
                        vertical: 16,
                        horizontal: 80,
                      ),
                    ),
                    child: const Text(
                      'Save Profile',
                      style: TextStyle(fontSize: 16),
                    ),
                  ),
                ],
              ),
            ),
          ),

          // Hiệu ứng opacity và spinner loading
          _isLoading
              ? Opacity(
                opacity: 0.5, // Làm nền mờ đi khi đang tải
                child: ModalBarrier(
                  dismissible: false, // Không cho phép đóng khi đang loading
                  color: Colors.black.withOpacity(0.5),
                ),
              )
              : Container(),

          // Hiển thị loading spinner khi đang tải
          _isLoading
              ? Center(child: CircularProgressIndicator(color: Colors.white))
              : Container(),
        ],
      ),
    );
  }
}
