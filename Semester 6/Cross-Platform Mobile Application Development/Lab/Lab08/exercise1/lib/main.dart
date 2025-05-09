// ignore_for_file: prefer_const_constructors, unused_import

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.deepPurple),
      home: UserPreferencesScreen(),
    ),
  );
}

class UserPreferencesScreen extends StatefulWidget {
  const UserPreferencesScreen({super.key});

  @override
  // ignore: library_private_types_in_public_api
  _UserPreferencesScreenState createState() => _UserPreferencesScreenState();
}

class _UserPreferencesScreenState extends State<UserPreferencesScreen> {
  String _language = 'English';
  String _colorTheme = 'Light';
  bool notification = false;
  String _displayName = '';
  String _videoQuality = '720p HD at 30 fps';
  late SharedPreferences _prefs;

  final List<String> videoQualities = [
    '720p HD at 30 fps',
    '1080p HD at 30 fps',
    '1080p HD at 60 fps',
    '4K at 30 fps',
    '4K at 60 fps',
  ];

  @override
  void initState() {
    super.initState();
    _loadPreferences();
  }

  Future<void> _loadPreferences() async {
    _prefs = await SharedPreferences.getInstance();
    setState(() {
      _language = _prefs.getString('language') ?? 'English';
      _colorTheme = _prefs.getString('colorTheme') ?? 'Light';
      notification = _prefs.getBool('notification') ?? false;
      _displayName = _prefs.getString('displayName') ?? '';
      _videoQuality = _prefs.getString('videoQuality') ?? '720p HD at 30 fps';
    });
  }

  Future<void> _savePreference(String key, dynamic value) async {
    if (value is String) {
      await _prefs.setString(key, value);
    } else if (value is bool) {
      await _prefs.setBool(key, value);
    }
  }

  void _onLanguageChanged(String? value) {
    if (value != null) {
      setState(() {
        _language = value;
      });
      _savePreference('language', value);
    }
  }

  void _onColorThemeChanged(String? value) {
    if (value != null) {
      setState(() {
        _colorTheme = value;
      });
      _savePreference('colorTheme', value);
    }
  }

  void _onVideoQualityChanged(String? value) {
    if (value != null) {
      setState(() {
        _videoQuality = value;
      });
      _savePreference('videoQuality', value);
      Navigator.pop(context);
    }
  }

  void _videoRecodingbottomSheet() {
    showModalBottomSheet(
      context: context,
      builder:
          (ctx) => Container(
            padding: EdgeInsets.all(20),
            child: ListView.builder(
              itemCount: videoQualities.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(videoQualities[index]),
                  onTap: () => _onVideoQualityChanged(videoQualities[index]),
                  trailing:
                      videoQualities[index] == _videoQuality
                          ? Icon(Icons.check)
                          : null,
                );
              },
            ),
          ),
    );
  }

  void _changeDisplayName() {
    TextEditingController controller = TextEditingController(
      text: _displayName,
    );
    showDialog(
      context: context,
      builder:
          (ctx) => AlertDialog(
            title: Text('Change Display Name'),
            content: TextFormField(
              controller: controller,
              decoration: InputDecoration(hintText: 'Enter display name'),
            ),
            actions: [
              TextButton(
                onPressed: () {
                  Navigator.pop(context);
                },
                child: Text('Close'),
              ),
              TextButton(
                onPressed: () {
                  setState(() {
                    _displayName = controller.text;
                  });
                  _savePreference('displayName', controller.text);
                  Navigator.pop(context);
                },
                child: Text('Save'),
              ),
            ],
          ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('User Preferences')),
      body: Padding(
        padding: EdgeInsets.all(20),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text(
                'Display Name',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              TextFormField(
                onTap: _changeDisplayName,
                readOnly: true,
                controller: TextEditingController(text: _displayName),
                decoration: InputDecoration(),
              ),
              SizedBox(height: 20),
              Text('Language', style: Theme.of(context).textTheme.bodySmall),
              DropdownButtonFormField<String>(
                value: _language,
                onChanged: _onLanguageChanged,
                items:
                    <String>[
                      'English',
                      'Spanish',
                      'French',
                      'German',
                    ].map<DropdownMenuItem<String>>((String value) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
              ),
              SizedBox(height: 20),
              Text('Color Theme', style: Theme.of(context).textTheme.bodySmall),
              DropdownButtonFormField<String>(
                value: _colorTheme,
                onChanged: _onColorThemeChanged,
                items:
                    <String>['Light', 'Dark'].map<DropdownMenuItem<String>>((
                      String value,
                    ) {
                      return DropdownMenuItem<String>(
                        value: value,
                        child: Text(value),
                      );
                    }).toList(),
              ),
              SizedBox(height: 20),
              Text(
                'Notification',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              SwitchListTile.adaptive(
                contentPadding: EdgeInsets.zero,
                value: notification,
                onChanged: (v) {
                  setState(() {
                    notification = v;
                  });
                  _savePreference('notification', v);
                },
                title: Text('Banners, Sounds, Badges'),
              ),
              SizedBox(height: 20),
              Text(
                'Record Video',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              TextFormField(
                onTap: _videoRecodingbottomSheet,
                readOnly: true,
                controller: TextEditingController(text: _videoQuality),
                decoration: InputDecoration(
                  suffixIcon: Icon(Icons.arrow_drop_down),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
