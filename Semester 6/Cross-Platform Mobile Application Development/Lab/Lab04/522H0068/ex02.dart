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
      title: 'Lab04 Ex02',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const HomeScreen(),
    );
  }
}

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  int _selectedIndex = 0;
  bool _isUnlocked = false;
  late PageController _pageController;

  @override
  void initState() {
    super.initState();
    _pageController = PageController(initialPage: _selectedIndex);
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
    _pageController.animateToPage(
      index,
      duration: const Duration(milliseconds: 300),
      curve: Curves.easeInOut,
    );
  }

  void _onPageChanged(int index) {
    if (!_isUnlocked && index == 4) {
      return;
    }
    setState(() {
      _selectedIndex = index;
    });
  }

  void _toggleSecretPage() {
    setState(() {
      _isUnlocked = !_isUnlocked;
      if (!_isUnlocked && _selectedIndex == 4) {
        _selectedIndex = 0;
        _pageController.jumpToPage(0);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Bottom NavBar'),
        backgroundColor: Colors.lightBlueAccent,
        foregroundColor: Colors.white,
        centerTitle: true,
        actions: [
          IconButton(
            icon: Icon(
              _isUnlocked ? Icons.lock_open : Icons.lock,
              color: Colors.white,
            ),
            onPressed: _toggleSecretPage,
          ),
        ],
      ),
      body: PageView(
        controller: _pageController,
        onPageChanged: _onPageChanged,
        children: [
          _buildPage('Home', Colors.blueAccent),
          _buildPage('History', Colors.greenAccent),
          _buildPage('Contacts', Colors.orangeAccent),
          _buildPage('Setting', Colors.purpleAccent),
          if (_isUnlocked) _buildPage('Secret', Colors.redAccent),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        items: [
          const BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          const BottomNavigationBarItem(
            icon: Icon(Icons.history),
            label: 'History',
          ),
          const BottomNavigationBarItem(
            icon: Icon(Icons.contacts),
            label: 'Contacts',
          ),
          const BottomNavigationBarItem(
            icon: Icon(Icons.settings),
            label: 'Setting',
          ),
          if (_isUnlocked)
            const BottomNavigationBarItem(
              icon: Icon(Icons.security),
              label: 'Secret',
            ),
        ],
        currentIndex: _selectedIndex,
        selectedItemColor: Colors.lightBlue,
        unselectedItemColor: Colors.grey,
        onTap: _onItemTapped,
        type: BottomNavigationBarType.fixed,
      ),
    );
  }

  Widget _buildPage(String title, Color color) {
    return Container(
      color: color.withOpacity(0.2),
      alignment: Alignment.center,
      child: Text(title, style: const TextStyle(fontSize: 40)),
    );
  }
}
