import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData.dark(),
      home: const InstagramProfile(),
    );
  }
}

class InstagramProfile extends StatelessWidget {
  const InstagramProfile({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Row(
          children: const [
            Text(
              'pkucpka.m',
              style: TextStyle(fontWeight: FontWeight.bold, fontSize: 20),
            ),
            SizedBox(width: 8),
            Icon(Icons.keyboard_arrow_down, size: 20),
          ],
        ),
        actions: const [
          Icon(Icons.add_box_outlined, size: 28),
          SizedBox(width: 16),
          Icon(Icons.menu, size: 28),
          SizedBox(width: 16),
        ],
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Stack(
                    children: [
                      Container(
                        width: 90,
                        height: 90,
                        decoration: const BoxDecoration(
                          shape: BoxShape.circle,
                          image: DecorationImage(
                            image: NetworkImage('https://picsum.photos/100'),
                            fit: BoxFit.cover,
                          ),
                        ),
                      ),
                      Positioned(
                        bottom: 0,
                        right: 0,
                        child: Container(
                          decoration: const BoxDecoration(
                            color: Colors.white,
                            shape: BoxShape.circle,
                          ),
                          padding: const EdgeInsets.all(5),
                          child: const Icon(
                            Icons.add,
                            color: Colors.black,
                            size: 20,
                          ),
                        ),
                      ),
                      Positioned(
                              top:28,
                              left: 13,
                              child: Container(
                                decoration: const BoxDecoration(
                                  shape: BoxShape.circle,
                                  color: Colors.grey,
                                ),
                                padding: const EdgeInsets.all(5),
                                child: const Icon(
                                  Icons.add,
                                  color: Colors.grey,
                                  size: 5,
                                ),
                              ),
                            ),
                      Positioned(
                        top: 0,
                        left: 5,
                        right: 5,
                        child: Stack(
                          clipBehavior: Clip.none, 
                          alignment: Alignment.topCenter,
                          children: [
                            Container(
                              decoration: const BoxDecoration(
                                borderRadius: BorderRadius.all(Radius.circular(10)),
                                color: Colors.grey,
                              ),
                              padding: const EdgeInsets.all(5),
                              child: Align(
                                alignment: Alignment.center,
                                child: Text(
                                  'Bạn đang nghĩ gì vậy?',
                                  style: TextStyle(
                                    color: Colors.grey[300],
                                    fontSize: 10,
                                  ),
                                ),
                              ),
                            ),
                            
                          ],
                        ),
                      )
                    ],
                  ),
                  const SizedBox(width: 20),
                  Expanded(
                    child: Column(
                      crossAxisAlignment:
                          CrossAxisAlignment.start, // Căn lề trái cho Text
                      children: [
                        // Đặt Text trong một Container với chiều rộng bằng với Row
                        Text('Phúc Phạm', style: TextStyle(fontSize: 15)),
                        const SizedBox(
                          height: 8,
                        ), // Khoảng cách giữa Text và Row
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            _buildStatColumn('16', 'bài viết'),
                            _buildStatColumn('1 triệu', 'người theo dõi'),
                            _buildStatColumn('1', 'đang theo dõi'),
                          ],
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Align(
                alignment: Alignment.centerLeft,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      'Blog cá nhân',
                      style: TextStyle(color: Color(0xFFA8A8A8)),
                    ),
                    const SizedBox(height: 2),
                    Text('Ai cũng phải bắt đầu từ đâu đó <3'),
                    const SizedBox(height: 8),
                  ],
                ),
              ),
            ),

            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 12.0),
              child: Align(
                alignment: Alignment.centerLeft,
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    Icon(Icons.facebook_sharp, color: Colors.grey[400]),
                    SizedBox(width: 8),
                    Text(
                      'Phúc Phạm',
                      style: TextStyle(fontWeight: FontWeight.bold),
                    ),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 10),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Column(
                children: [
                  Container(
                    width: double.infinity,
                    padding: const EdgeInsets.all(12),
                    decoration: BoxDecoration(
                      color: Colors.grey[800],
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Text(
                          "Công cụ chuyên nghiệp",
                          style: const TextStyle(
                            fontWeight: FontWeight.bold,
                            fontSize: 16,
                          ),
                        ),
                        const SizedBox(height: 4),
                        Text(
                          "2 triệu lượt xem trong 30 ngày qua",
                          style: TextStyle(
                            color: Colors.grey[400],
                            fontSize: 14,
                          ),
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 10),
                  Row(
                    children: [
                      Expanded(child: _buildButton('Chỉnh sửa trang cá nhân')),
                      const SizedBox(width: 8),
                      Expanded(child: _buildButton('Chia sẻ trang cá nhân')),
                    ],
                  ),
                ],
              ),
            ),
            const SizedBox(height: 10),
            SizedBox(
              height: 100,
              child: ListView(
                scrollDirection: Axis.horizontal,
                padding: const EdgeInsets.symmetric(horizontal: 16),
                children: [
                  _buildStoryHighlight(Icons.add, 'Mới'),
                  _buildStoryHighlight(null, '🤞🤞', imageIndex: 20),
                  _buildStoryHighlight(null, '💟', imageIndex: 30),
                ],
              ),
            ),
            const SizedBox(height: 10),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                Column(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    const Icon(Icons.grid_on, size: 28),
                    Container(
                      margin: const EdgeInsets.only(top: 4), 
                      width: 24, 
                      height: 2, 
                      color: Colors.white, 
                    ),
                  ],
                ),
                const Icon(Icons.ondemand_video_outlined, size: 28),
                const Icon(Icons.person_pin_outlined, size: 28),
              ],
            ),
            SizedBox(height: 10),
            GridView.builder(
              shrinkWrap: true,
              physics: const NeverScrollableScrollPhysics(),
              gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                crossAxisCount: 3,
                crossAxisSpacing: 2,
                mainAxisSpacing: 2,
              ),
              itemCount: 12,
              itemBuilder: (context, index) {
                return Image.network(
                  'https://picsum.photos/100?image=${index + 100}',
                  fit: BoxFit.cover,
                );
              },
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatColumn(String count, String label) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          count,
          style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
        Text(label, style: TextStyle(color: Colors.grey[400])),
      ],
    );
  }

  Widget _buildButton(String label) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 8),
      decoration: BoxDecoration(
        color: Colors.grey[800],
        borderRadius: BorderRadius.circular(10),
      ),
      child: Center(
        child: Text(label, style: const TextStyle(fontWeight: FontWeight.bold)),
      ),
    );
  }

  Widget _buildStoryHighlight(IconData? icon, String label, {int? imageIndex}) {
    return Padding(
      padding: const EdgeInsets.only(right: 16.0),
      child: Column(
        children: [
          Container(
            width: 70,
            height: 70,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              border: Border.all(color: Colors.grey[800]!, width: 4),
              boxShadow: [
                BoxShadow(
                  color: Colors.black,
                  spreadRadius: 2,
                  blurRadius: 4,
                  offset: Offset(0, 2),
                ),
              ],
            ),
            child: Center(
              child:
                  icon != null
                      ? Icon(icon, size: 30, color: Colors.white)
                      : Container(
                        width: 65,
                        height: 65,
                        decoration: BoxDecoration(
                          shape: BoxShape.circle,
                          image: DecorationImage(
                            image: NetworkImage(
                              'https://picsum.photos/100?image=${imageIndex ?? 1}',
                            ),
                            fit: BoxFit.cover,
                          ),
                          border: Border.all(color: Colors.black, width: 3),
                        ),
                      ),
            ),
          ),
          const SizedBox(height: 4),
          Text(label, style: const TextStyle(fontSize: 12)),
        ],
      ),
    );
  }
}
