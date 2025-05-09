// ignore_for_file: prefer_const_constructors, unused_import

import 'package:flutter/material.dart';

void main() {
  runApp(MaterialApp(
    debugShowCheckedModeBanner: false,
    theme: ThemeData(primarySwatch: Colors.deepPurple),
    home: MyApp(),
  ));
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('File Manager'),
      ),
      body: Padding(
        padding: EdgeInsets.symmetric(horizontal: 20),
        child: Column(
          children: [
            SizedBox(height: 24),
            TextFormField(
              decoration: InputDecoration(
                labelText: 'File Name',
                border: OutlineInputBorder(),
              ),
            ),
            SizedBox(height: 8),
            TextFormField(
              maxLines: 5,
              decoration: InputDecoration(
                labelText: 'Content',
                border: OutlineInputBorder(),
              ),
            ),
            SizedBox(height: 16),
            Row(
              children: [
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {},
                    child: Text('Save or Update'),
                    style: ElevatedButton.styleFrom(
                        minimumSize: Size.fromHeight(50)),
                  ),
                ),
                SizedBox(
                  width: 12,
                ),
                Expanded(
                  child: ElevatedButton(
                    onPressed: () {},
                    child: Text('Read file'),
                    style: ElevatedButton.styleFrom(
                        minimumSize: Size.fromHeight(50)),
                  ),
                ),
              ],
            ),
            SizedBox(height: 12),
            Expanded(
                child: ListView.separated(
                    itemBuilder: (ctx, idx) => Divider(
                          height: 1,
                        ),
                    separatorBuilder: (ctx, idx) => ListTile(
                          onTap: () {},
                          title: Text('File ${idx + 1}.txt'),
                          trailing: IconButton(
                            icon: Icon(Icons.delete),
                            onPressed: () {},
                          ),
                        ),
                    itemCount: 10))
          ],
        ),
      ),
    );
  }
}
