// ignore_for_file: prefer_const_constructors, unused_import

import 'dart:io';
import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';

void main() {
  runApp(
    MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.deepPurple),
      home: FileManagerApp(),
    ),
  );
}

class FileManagerApp extends StatefulWidget {
  const FileManagerApp({super.key});

  @override
  _FileManagerAppState createState() => _FileManagerAppState();
}

class _FileManagerAppState extends State<FileManagerApp> {
  final _fileNameController = TextEditingController();
  final _contentController = TextEditingController();
  final _fileNameFocusNode = FocusNode();
  List<String> _files = [];

  @override
  void initState() {
    super.initState();
    _loadFiles();
  }

  @override
  void dispose() {
    _fileNameController.dispose();
    _contentController.dispose();
    _fileNameFocusNode.dispose();
    super.dispose();
  }

  Future<String> _getAppDocumentsPath() async {
    final directory = await getApplicationDocumentsDirectory();
    return directory.path;
  }

  Future<void> _loadFiles() async {
    try {
      final path = await _getAppDocumentsPath();
      final dir = Directory(path);
      final files =
          await dir
              .list()
              .where((entity) => entity is File && entity.path.endsWith('.txt'))
              .map((entity) => entity.path.split(Platform.pathSeparator).last)
              .toList();
      setState(() {
        _files = files;
      });
    } catch (e) {
      _showError('Failed to load files: $e');
    }
  }

  Future<void> _saveFile() async {
    final fileName = _fileNameController.text.trim();
    final content = _contentController.text;

    if (fileName.isEmpty) {
      _showError('Please enter a file name');
      return;
    }

    try {
      final path = await _getAppDocumentsPath();
      final file = File('$path/$fileName.txt');
      await file.writeAsString(content);

      if (!_files.contains('$fileName.txt')) {
        setState(() {
          _files.add('$fileName.txt');
        });
      }

      _fileNameController.clear();
      _contentController.clear();
      _fileNameFocusNode.requestFocus();

      ScaffoldMessenger.of(
        context,
      ).showSnackBar(SnackBar(content: Text('File saved successfully')));
    } catch (e) {
      _showError('Failed to save file: $e');
    }
  }

  Future<void> _readFile(String fileName) async {
    try {
      final path = await _getAppDocumentsPath();
      final file = File('$path/$fileName');
      if (await file.exists()) {
        final content = await file.readAsString();
        _contentController.text = content;
        _fileNameController.text = fileName.replaceAll('.txt', '');
      } else {
        _showError('File does not exist');
      }
    } catch (e) {
      _showError('Failed to read file: $e');
    }
  }

  Future<void> _readFileFromList(String fileName) async {
    try {
      final path = await _getAppDocumentsPath();
      final file = File('$path/$fileName');
      if (await file.exists()) {
        final content = await file.readAsString();
        _fileNameController.text = fileName.replaceAll('.txt', '');
        _contentController.text = content;
      } else {
        _showError('File does not exist');
      }
    } catch (e) {
      _showError('Failed to read file: $e');
    }
  }

  Future<void> _deleteFile(String fileName) async {
    try {
      final path = await _getAppDocumentsPath();
      final file = File('$path/$fileName');
      if (await file.exists()) {
        await file.delete();
        setState(() {
          _files.remove(fileName);
        });
        ScaffoldMessenger.of(
          context,
        ).showSnackBar(SnackBar(content: Text('File deleted successfully')));
      }
    } catch (e) {
      _showError('Failed to delete file: $e');
    }
  }

  void _showError(String message) {
    showDialog(
      context: context,
      builder:
          (ctx) => AlertDialog(
            title: Text('Error'),
            content: Text(message),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(context),
                child: Text('OK'),
              ),
            ],
          ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('File Manager')),
      body: Padding(
        padding: EdgeInsets.symmetric(horizontal: 20),
        child: Column(
          children: [
            SizedBox(height: 24),
            TextFormField(
              controller: _fileNameController,
              focusNode: _fileNameFocusNode,
              decoration: InputDecoration(
                labelText: 'File Name',
                border: OutlineInputBorder(),
              ),
            ),
            SizedBox(height: 8),
            TextFormField(
              controller: _contentController,
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
                    onPressed: _saveFile,
                    style: ElevatedButton.styleFrom(
                      minimumSize: Size.fromHeight(50),
                    ),
                    child: Text('Save or Update'),
                  ),
                ),
                SizedBox(width: 12),
                Expanded(
                  child: ElevatedButton(
                    onPressed:
                        () =>
                            _readFile('${_fileNameController.text.trim()}.txt'),
                    style: ElevatedButton.styleFrom(
                      minimumSize: Size.fromHeight(50),
                    ),
                    child: Text('Read File'),
                  ),
                ),
              ],
            ),
            SizedBox(height: 12),
            Expanded(
              child: ListView.separated(
                itemCount: _files.length,
                separatorBuilder: (ctx, idx) => Divider(height: 1),
                itemBuilder:
                    (ctx, idx) => ListTile(
                      onTap: () => _readFileFromList(_files[idx]),
                      title: Text(_files[idx]),
                      trailing: IconButton(
                        icon: Icon(Icons.delete),
                        onPressed: () => _deleteFile(_files[idx]),
                      ),
                    ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
