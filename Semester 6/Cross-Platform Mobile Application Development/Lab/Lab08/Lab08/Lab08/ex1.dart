// ignore_for_file: prefer_const_constructors, unused_import

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() async {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    theme: ThemeData(primarySwatch: Colors.deepPurple),
    home: UserPreferencesScreen(),
  ));
}

class UserPreferencesScreen extends StatefulWidget {
  const UserPreferencesScreen({Key? key}) : super(key: key);

  @override
  _UserPreferencesScreenState createState() => _UserPreferencesScreenState();
}

class _UserPreferencesScreenState extends State<UserPreferencesScreen> {
  String _language = 'English';
  String _colorTheme = 'Light';
  bool notification = false;
  var videoQualities = [
    '720p HD at 30 fps',
    '1080p HD at 30 fps',
    '1080p HD at 60 fps',
    '4K at 30 fps',
    '4K at 60 fps'
  ];

  void _onLanguageChanged(String? value) {
    setState(() {
      _language = value ?? '';
    });
  }

  void _onColorThemeChanged(String? value) {
    setState(() {
      _colorTheme = value ?? '';
    });
  }

  void _videoRecodingbottomSheet() {
    showModalBottomSheet(
        context: context,
        builder: (ctx) => Container(
              alignment: Alignment.center,
              child: Text('Hiển thị ListView ở đây'),
            ));
  }

  void _changeDisplayName() {
    showDialog(
        context: context,
        builder: (ctx) => AlertDialog(
              title: Text('Change Display Name'),
              content: Text('Đặt TextFormField widget ở đây'),
              actions: [
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text('Close'),
                ),
                TextButton(
                  onPressed: () {
                    Navigator.pop(context);
                  },
                  child: Text('Save'),
                )
              ],
            ));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('User Preferences'),
      ),
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
                decoration: InputDecoration(),
              ),
              SizedBox(height: 20),
              Text(
                'Language',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              DropdownButtonFormField<String>(
                value: _language,
                onChanged: _onLanguageChanged,
                items: <String>['English', 'Spanish', 'French', 'German']
                    .map<DropdownMenuItem<String>>((String value) {
                  return DropdownMenuItem<String>(
                    value: value,
                    child: Text(value),
                  );
                }).toList(),
              ),
              SizedBox(height: 20),
              Text(
                'Color Theme',
                style: Theme.of(context).textTheme.bodySmall,
              ),
              DropdownButtonFormField<String>(
                value: _colorTheme,
                onChanged: _onColorThemeChanged,
                items: <String>['Light', 'Dark']
                    .map<DropdownMenuItem<String>>((String value) {
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
                decoration:
                    InputDecoration(suffixIcon: Icon(Icons.arrow_drop_down)),
              )
            ],
          ),
        ),
      ),
    );
  }
}
