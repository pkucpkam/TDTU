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
      title: 'Gmail Clone',
      theme: ThemeData.dark(),
      home: const GmailHomePage(),
    );
  }
}

class GmailHomePage extends StatefulWidget {
  const GmailHomePage({super.key});

  @override
  _GmailHomePageState createState() => _GmailHomePageState();
}

class _GmailHomePageState extends State<GmailHomePage> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      drawer: Drawer(
        child: ListView(
          padding: EdgeInsets.zero,
          children: [
            Container(
              padding: const EdgeInsets.only(
                top: 50,
                bottom: 5,
                left: 30,
                right: 30,
              ),
              child: Row(
                children: [
                  Image.network(
                    'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7e/Gmail_icon_%282020%29.svg/1280px-Gmail_icon_%282020%29.svg.png',
                    width: 30,
                    height: 30,
                  ),
                  const SizedBox(width: 10),
                  const Text(
                    'Gmail',
                    style: TextStyle(color: Colors.white, fontSize: 20),
                  ),
                ],
              ),
            ),
            Divider(color: Colors.grey[700]),
            Column(
              children: [
                DrawerMenuItem(
                  icon: Icons.all_inbox,
                  title: 'Tất cả hộp thư đến',
                  badgeCount: '99+',
                ),
                Padding(
                  padding: const EdgeInsets.only(left: 70),
                  child: Divider(color: Colors.grey[700]),
                ),

                DrawerMenuItem(
                  icon: Icons.inbox,
                  title: 'Chính',
                  badgeCount: '99+',
                ),
                DrawerMenuItem(
                  icon: Icons.people,
                  title: 'Mạng xã hội',
                  tag: '63 mới',
                ),
                DrawerMenuItem(
                  icon: Icons.discount,
                  title: 'Quảng cáo',
                  tag: '90 mới',
                ),
                DrawerMenuItem(
                  icon: Icons.info_outline,
                  title: 'Nội dung cập nhật',
                  tag: '10 mới',
                ),
                Padding(
                  padding: const EdgeInsets.only(top: 5, left: 70),
                  child: Divider(color: Colors.grey[700]),
                ),

                DrawerMenuItem(
                  icon: Icons.star_outline,
                  title: 'Có gắn dấu sao',
                ),
                DrawerMenuItem(
                  icon: Icons.access_time_outlined,
                  title: 'Đã ẩn',
                ),
                DrawerMenuItem(
                  icon: Icons.label_important_outline,
                  title: 'Quan trọng',
                  badgeCount: '83',
                ),
                DrawerMenuItem(icon: Icons.send_outlined, title: 'Đã gửi'),
                DrawerMenuItem(icon: Icons.schedule_send, title: 'Đã lên lịch'),
                DrawerMenuItem(
                  icon: Icons.send_time_extension_outlined,
                  title: 'Hộp thư đi',
                ),
                DrawerMenuItem(
                  icon: Icons.file_copy_outlined,
                  title: 'Thư nháp',
                  badgeCount: '7',
                ),
                DrawerMenuItem(icon: Icons.mail_outline, title: 'Tất cả thư'),
                DrawerMenuItem(icon: Icons.report_outlined, title: 'Thư rác'),
                DrawerMenuItem(icon: Icons.delete_outline, title: 'Thùng rác'),

                Padding(
                  padding: const EdgeInsets.only(left: 70),
                  child: Divider(color: Colors.grey[700]),
                ),
                DrawerMenuItem(icon: Icons.add, title: 'Tạo mới'),
                Padding(
                  padding: const EdgeInsets.only(left: 70),
                  child: Divider(color: Colors.grey[700]),
                ),

                DrawerMenuItem(icon: Icons.settings_outlined, title: 'Cài đặt'),
                DrawerMenuItem(
                  icon: Icons.message_outlined,
                  title: 'Gửi ý kiến phản hồi',
                ),
                DrawerMenuItem(icon: Icons.help_outline, title: 'Trợ giúp'),
              ],
            ),
          ],
        ),
      ),
      body: SingleChildScrollView(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 10),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.grey[850],
                  borderRadius: BorderRadius.circular(10),
                ),
                padding: const EdgeInsets.symmetric(
                  horizontal: 10,
                  vertical: 2,
                ),
                child: Row(
                  children: [
                    IconButton(
                      icon: const Icon(
                        Icons.menu,
                        size: 28,
                        color: Colors.white,
                      ),
                      onPressed: () {
                        _scaffoldKey.currentState?.openDrawer();
                      },
                    ),
                    Expanded(
                      child: TextField(
                        decoration: InputDecoration(
                          hintText: 'Tìm trong thư',
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30),
                            borderSide: BorderSide.none,
                          ),
                        ),
                      ),
                    ),
                    const SizedBox(width: 10),
                    const CircleAvatar(
                      backgroundImage: NetworkImage(
                        'https://img.freepik.com/free-vector/smiling-young-man-illustration_1308-174669.jpg',
                      ),
                    ),
                  ],
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 16.0,
                vertical: 8.0,
              ),
              child: Text('Chính', style: TextStyle(fontSize: 14)),
            ),
            // Danh sách email, bọc trong Column vì SingleChildScrollView không hỗ trợ ListView
            Column(
              children: const [
                EmailTile(
                  avtUrl:
                      'https://upload.wikimedia.org/wikipedia/commons/0/05/Facebook_Logo_%282019%29.png',
                  sender: 'Facebook',
                  subject: 'New friend request',
                  preview: 'John Doe sent you a friend request.',
                  time: '9:30 AM',
                  isRead: true,
                ),
                EmailTile(
                  avtUrl:
                      'https://www.citypng.com/public/uploads/preview/netflix-vector-flat-logo-735811696261671nhzlvgcmyf.png',
                  sender: 'Netflix',
                  subject: 'New episode available!',
                  preview:
                      'A new episode of your favorite show is now available.',
                  time: '10:00 AM',
                  isRead: true,
                ),
                EmailTile(
                  avtUrl:
                      'https://cdn-icons-png.flaticon.com/512/2496/2496110.png',
                  sender: 'Twitter',
                  subject: 'Someone mentioned you',
                  preview: 'You were mentioned in a tweet by @johndoe.',
                  time: '10:15 AM',
                ),
                EmailTile(
                  avtUrl:
                      'https://i.pinimg.com/originals/01/ca/da/01cada77a0a7d326d85b7969fe26a728.jpg',
                  sender: 'Amazon',
                  subject: 'Your order has shipped',
                  preview: 'Your order #123456789 has been shipped.',
                  time: '11:00 AM',
                  isRead: true,
                ),
                EmailTile(
                  avtUrl:
                      'https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/LinkedIn_icon.svg/2048px-LinkedIn_icon.svg.png',
                  sender: 'LinkedIn',
                  subject: 'Job opportunity for you',
                  preview:
                      'A new job opportunity that matches your profile has been posted.',
                  time: '11:30 AM',
                ),
                EmailTile(
                  avtUrl:
                      'https://upload.wikimedia.org/wikipedia/commons/thumb/c/c1/Google_%22G%22_logo.svg/800px-Google_%22G%22_logo.svg.png',
                  sender: 'Google',
                  subject: 'Security alert',
                  preview:
                      'We noticed a suspicious sign-in attempt on your account.',
                  time: '12:00 PM',
                ),
                EmailTile(
                  avtUrl:
                      'https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Slack_icon_2019.svg/2048px-Slack_icon_2019.svg.png',
                  sender: 'Slack',
                  subject: 'New message in #general',
                  preview:
                      'There is a new message from Jane in the #general channel.',
                  time: '1:00 PM',
                ),
                EmailTile(
                  avtUrl:
                      'https://storage.googleapis.com/pr-newsroom-wp/1/2023/05/Spotify_Primary_Logo_RGB_Green.png',
                  sender: 'Spotify',
                  subject: 'Playlist recommendation',
                  preview: 'We found a playlist you might like: "Chill Vibes".',
                  time: '2:00 PM',
                  isRead: true,
                ),
              ],
            ),
          ],
        ),
      ),

      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {},
        icon: const Icon(Icons.edit_rounded, color: Colors.redAccent),
        backgroundColor: Colors.grey[800],
        label: const Text(
          'Soạn thư',
          style: TextStyle(color: Colors.redAccent),
        ),
      ),

      bottomNavigationBar: BottomNavigationBar(
        backgroundColor: Colors.grey[900],
        selectedItemColor: Colors.blue,
        unselectedItemColor: Colors.white60,
        showSelectedLabels: false,
        showUnselectedLabels: false,
        items: [
          BottomNavigationBarItem(
            icon: Stack(
              clipBehavior: Clip.none,
              children: [
                Icon(Icons.mail, size: 30),
                Positioned(
                  top: -3,
                  right: -6,
                  child: Container(
                    padding: EdgeInsets.all(4),
                    decoration: BoxDecoration(
                      color: Colors.red,
                      shape: BoxShape.circle,
                    ),
                    constraints: BoxConstraints(minWidth: 18, minHeight: 18),
                    child: Text(
                      '5', // Số lượng tin nhắn chưa đọc
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 10,
                        fontWeight: FontWeight.bold,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ),
                ),
              ],
            ),
            label: '',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.video_call, size: 30),
            label: '',
          ),
        ],
      ),
    );
  }
}

class EmailTile extends StatelessWidget {
  final String avtUrl;
  final String sender;
  final String subject;
  final String preview;
  final String time;
  final bool isRead;

  const EmailTile({
    required this.avtUrl,
    required this.sender,
    required this.subject,
    required this.preview,
    required this.time,
    this.isRead = false,
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          CircleAvatar(
            backgroundImage: NetworkImage(avtUrl),
            onBackgroundImageError:
                (_, __) => const Icon(Icons.error, color: Colors.red),
          ),

          const SizedBox(width: 12),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Expanded(
                      child: Text(
                        sender,
                        style: TextStyle(
                          fontWeight:
                              isRead ? FontWeight.normal : FontWeight.bold,
                          color: isRead ? Colors.grey : Colors.white,
                          fontSize: 16,
                        ),
                        overflow: TextOverflow.ellipsis,
                      ),
                    ),
                    Text(
                      time,
                      style: TextStyle(
                        fontSize: 13,
                        fontWeight:
                            isRead ? FontWeight.normal : FontWeight.bold,
                        color: isRead ? Colors.grey : Colors.white,
                      ),
                    ),
                  ],
                ),
                Text(
                  subject,
                  style: TextStyle(
                    fontWeight: isRead ? FontWeight.normal : FontWeight.bold,
                    color: isRead ? Colors.grey : Colors.white,
                  ),
                ),
                Row(
                  children: [
                    Expanded(
                      child: Text(
                        preview,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                        style: TextStyle(color: Colors.grey),
                      ),
                    ),
                    Icon(Icons.star_border, color: Colors.grey, size: 18),
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class DrawerMenuItem extends StatelessWidget {
  final IconData icon;
  final String title;
  final String? badgeCount;
  final String tag;

  const DrawerMenuItem({
    required this.icon,
    required this.title,
    this.badgeCount,
    this.tag = '',
    super.key,
  });

  Color _getTagColor(String title) {
    switch (title) {
      case 'Mạng xã hội':
        return Colors.blueAccent;
      case 'Quảng cáo':
        return Colors.greenAccent;
      case 'Nội dung cập nhật':
        return Colors.orangeAccent;
      default:
        return Colors.grey;
    }
  }

  @override
  Widget build(BuildContext context) {
    Color tagColor = _getTagColor(title);
    return Stack(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 30),
          child: Container(
            decoration: BoxDecoration(borderRadius: BorderRadius.circular(8)),
            padding: const EdgeInsets.all(10),
            child: Row(
              children: [
                Icon(icon, size: 24),
                const SizedBox(width: 12),
                Expanded(
                  child: Text(title, style: const TextStyle(fontSize: 14)),
                ),
                if (badgeCount != null)
                  Text(
                    badgeCount!,
                    style: const TextStyle(color: Colors.grey, fontSize: 12),
                  ),
                if (tag.isNotEmpty)
                  Container(
                    padding: const EdgeInsets.symmetric(
                      horizontal: 6,
                      vertical: 1,
                    ),
                    decoration: BoxDecoration(
                      color: tagColor,
                      borderRadius: BorderRadius.circular(10),
                    ),
                    child: Text(
                      tag,
                      style: const TextStyle(color: Colors.black, fontSize: 12),
                    ),
                  ),
              ],
            ),
          ),
        ),

        if (title == "Chính")
          Positioned(
            top: 5,
            left: 0,
            right: 20,
            bottom: 5,
            child: Container(
              decoration: BoxDecoration(
                color: Colors.redAccent.withOpacity(0.2),
                borderRadius: BorderRadius.only(
                  topRight: Radius.circular(30),
                  bottomRight: Radius.circular(30),
                ),
              ),
            ),
          ),
      ],
    );
  }
}
