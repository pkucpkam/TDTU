import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(NotesApp());
}

class NotesApp extends StatelessWidget {
  const NotesApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: NotesScreen(),
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.purple),
    );
  }
}

class NotesScreen extends StatefulWidget {
  const NotesScreen({super.key});

  @override
  _NotesScreenState createState() => _NotesScreenState();
}

class _NotesScreenState extends State<NotesScreen> {
  List<Note> notes = [];
  bool isGridView = false;
  late Database _database;

  @override
  void initState() {
    super.initState();
    _initDatabase();
  }

  Future<void> _initDatabase() async {
    final databasesPath = await getDatabasesPath();
    final path = join(databasesPath, 'notes.db');

    _database = await openDatabase(
      path,
      version: 1,
      onCreate: (db, version) async {
        await db.execute('''
          CREATE TABLE notes (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title TEXT NOT NULL,
            content TEXT,
            color INTEGER,
            password TEXT
          )
        ''');
      },
    );

    await _loadNotes();
  }

  Future<void> _loadNotes() async {
    final List<Map<String, dynamic>> maps = await _database.query('notes');
    setState(() {
      notes = maps.map((map) => Note.fromMap(map)).toList();
    });
  }

  Future<void> _addNote() async {
    final newNote = Note(
      title: 'New Note ${notes.length + 1}',
      content: '',
      color: Colors.yellow,
    );
    final id = await _database.insert('notes', newNote.toMap());
    setState(() {
      notes.add(newNote.copyWith(id: id));
    });
  }

  Future<void> _updateNote(Note note) async {
    await _database.update(
      'notes',
      note.toMap(),
      where: 'id = ?',
      whereArgs: [note.id],
    );
    await _loadNotes();
  }

  Future<void> _deleteNote(Note note) async {
    await _database.delete('notes', where: 'id = ?', whereArgs: [note.id]);
    setState(() {
      notes.remove(note);
    });
  }

  void _toggleViewMode() {
    setState(() {
      isGridView = !isGridView;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Quản Lý Ghi Chú'),
        backgroundColor: Colors.purple,
        foregroundColor: Colors.white,
        actions: [
          IconButton(
            icon: Icon(isGridView ? Icons.list : Icons.grid_view),
            onPressed: _toggleViewMode,
          ),
        ],
      ),
      body: isGridView ? _buildGridView() : _buildListView(),
      floatingActionButton: FloatingActionButton(
        onPressed: _addNote,
        child: const Icon(Icons.add),
      ),
    );
  }

  Widget _buildListView() {
    return ListView.builder(
      itemCount: notes.length,
      itemBuilder: (context, index) {
        return Slidable(
          key: ValueKey(notes[index].id),
          startActionPane: ActionPane(
            motion: const ScrollMotion(),
            children: [
              if (notes[index].hasPassword) ...[
                SlidableAction(
                  onPressed: (_) => _changePassword(notes[index]),
                  backgroundColor: Colors.cyan,
                  icon: Icons.lock_open,
                  label: 'Thay Đổi',
                ),
                SlidableAction(
                  onPressed: (_) => _removePassword(notes[index]),
                  backgroundColor: Colors.cyan,
                  icon: Icons.lock_open,
                  label: 'Gỡ Bảo Vệ',
                ),
              ] else
                SlidableAction(
                  onPressed: (_) => _setPassword(notes[index]),
                  backgroundColor: Colors.cyan,
                  icon: Icons.lock,
                  label: 'Bảo Vệ',
                ),
              SlidableAction(
                onPressed: (_) => _deleteNoteConfirmation(notes[index]),
                backgroundColor: Colors.red,
                icon: Icons.delete,
                label: 'Xóa',
              ),
            ],
          ),
          child: ListTile(
            title: Text(notes[index].title),
            trailing: notes[index].hasPassword ? const Icon(Icons.lock) : null,
            tileColor: notes[index].color?.withOpacity(0.2),
            onTap: () => _viewNoteContent(notes[index]),
            onLongPress: () => _changeNoteColor(notes[index]),
          ),
        );
      },
    );
  }

  Widget _buildGridView() {
    return GridView.builder(
      gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
        crossAxisCount: 2,
        childAspectRatio: 1,
      ),
      itemCount: notes.length,
      itemBuilder: (context, index) {
        return GestureDetector(
          onTap: () => _viewNoteContent(notes[index]),
          onLongPress: () => _showBottomSheet(notes[index]),
          child: Card(
            color: notes[index].color ?? Colors.yellow,
            child: Center(
              child: Text(
                notes[index].title,
                style: const TextStyle(fontWeight: FontWeight.bold),
              ),
            ),
          ),
        );
      },
    );
  }

  void _setPassword(Note note) {
    String? newPassword;
    String? confirmPassword;

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Đặt Mật Khẩu'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                decoration: const InputDecoration(labelText: 'Mật Khẩu'),
                obscureText: true,
                onChanged: (value) => newPassword = value,
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'Xác Nhận'),
                obscureText: true,
                onChanged: (value) => confirmPassword = value,
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                if (newPassword == null || newPassword!.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      content: Text('Mật khẩu không được để trống'),
                    ),
                  );
                  return;
                }
                if (newPassword != confirmPassword) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Mật khẩu không khớp')),
                  );
                  return;
                }
                setState(() {
                  note.password = newPassword;
                });
                _updateNote(note);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Đã đặt mật khẩu')),
                );
              },
              child: const Text('Bảo Vệ'),
            ),
          ],
        );
      },
    );
  }

  void _changePassword(Note note) {
    String? oldPassword;
    String? newPassword;
    String? confirmPassword;

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Thay Đổi Mật Khẩu'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                decoration: const InputDecoration(labelText: 'Mật Khẩu Cũ'),
                obscureText: true,
                onChanged: (value) => oldPassword = value,
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'Mật Khẩu Mới'),
                obscureText: true,
                onChanged: (value) => newPassword = value,
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'Xác Nhận'),
                obscureText: true,
                onChanged: (value) => confirmPassword = value,
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                if (oldPassword != note.password) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Mật khẩu cũ không đúng')),
                  );
                  return;
                }
                if (newPassword == null || newPassword!.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      content: Text('Mật khẩu mới không được để trống'),
                    ),
                  );
                  return;
                }
                if (newPassword != confirmPassword) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Mật khẩu mới không khớp')),
                  );
                  return;
                }
                setState(() {
                  note.password = newPassword;
                });
                _updateNote(note);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Đã thay đổi mật khẩu')),
                );
              },
              child: const Text('Thay Đổi'),
            ),
          ],
        );
      },
    );
  }

  void _removePassword(Note note) {
    String? confirmPassword;

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Xóa Mật Khẩu'),
          content: TextField(
            decoration: const InputDecoration(labelText: 'Nhập Mật Khẩu'),
            obscureText: true,
            onChanged: (value) => confirmPassword = value,
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                if (confirmPassword != note.password) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Mật khẩu không đúng')),
                  );
                  return;
                }
                setState(() {
                  note.password = null;
                });
                _updateNote(note);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Đã xóa mật khẩu')),
                );
              },
              child: const Text('Xóa'),
            ),
          ],
        );
      },
    );
  }

  void _deleteNoteConfirmation(Note note) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Xóa Ghi Chú'),
          content: const Text('Bạn có chắc muốn xóa ghi chú này?'),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                final deletedNote = note;
                _deleteNote(note);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: const Text('Đã xóa ghi chú'),
                    action: SnackBarAction(
                      label: 'Hoàn Tác',
                      onPressed: () {
                        setState(() {
                          notes.add(deletedNote);
                          _database.insert('notes', deletedNote.toMap());
                        });
                      },
                    ),
                  ),
                );
              },
              child: const Text('Xóa'),
            ),
          ],
        );
      },
    );
  }

  void _viewNoteContent(Note note) async {
    if (note.hasPassword) {
      bool? canView = await _passwordProtectedDialog(note);
      if (canView != true) return;
    }
    final updatedNote = await Navigator.push<Note>(
      context,
      MaterialPageRoute(builder: (context) => NoteDetail(note: note)),
    );
    if (updatedNote != null) {
      setState(() {
        final index = notes.indexWhere((n) => n.id == note.id);
        if (index != -1) {
          notes[index] = updatedNote;
          _updateNote(updatedNote);
        }
      });
    }
  }

  Future<bool?> _passwordProtectedDialog(Note note) async {
    String? enteredPassword;

    return showDialog<bool>(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Nhập Mật Khẩu'),
          content: TextField(
            decoration: const InputDecoration(labelText: 'Mật Khẩu'),
            obscureText: true,
            onChanged: (value) => enteredPassword = value,
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context, false),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                if (enteredPassword == note.password) {
                  Navigator.pop(context, true);
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Mật khẩu không đúng')),
                  );
                }
              },
              child: const Text('Xác Nhận'),
            ),
          ],
        );
      },
    );
  }

  void _showBottomSheet(Note note) {
    showModalBottomSheet(
      context: context,
      builder: (BuildContext context) {
        // Sửa Context thành BuildContext
        return Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            ListTile(
              leading: const Icon(Icons.delete),
              title: const Text('Xóa'),
              onTap: () {
                Navigator.pop(context);
                _deleteNoteConfirmation(note);
              },
            ),
            if (note.hasPassword) ...[
              ListTile(
                leading: const Icon(Icons.lock),
                title: const Text('Thay Đổi Mật Khẩu'),
                onTap: () {
                  Navigator.pop(context);
                  _changePassword(note);
                },
              ),
              ListTile(
                leading: const Icon(Icons.lock_open),
                title: const Text('Xóa Mật Khẩu'),
                onTap: () {
                  Navigator.pop(context);
                  _removePassword(note);
                },
              ),
            ] else
              ListTile(
                leading: const Icon(Icons.lock),
                title: const Text('Bảo Vệ'),
                onTap: () {
                  Navigator.pop(context);
                  _setPassword(note);
                },
              ),
            ListTile(
              leading: const Icon(Icons.color_lens),
              title: const Text('Thay Đổi Màu'),
              onTap: () {
                Navigator.pop(context);
                _changeNoteColor(note);
              },
            ),
          ],
        );
      },
    );
  }

  void _changeNoteColor(Note note) {
    Color selectedColor = note.color ?? Colors.yellow;
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Chọn Màu Ghi Chú'),
          content: SingleChildScrollView(
            child: BlockPicker(
              pickerColor: selectedColor,
              onColorChanged: (color) => selectedColor = color,
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                setState(() {
                  note.color = selectedColor;
                });
                _updateNote(note);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text('Đã thay đổi màu ghi chú')),
                );
              },
              child: const Text('Lưu'),
            ),
          ],
        );
      },
    );
  }
}

class Note {
  final int? id;
  String title;
  String? password;
  String content;
  Color? color;

  Note({
    this.id,
    required this.title,
    this.password,
    this.content = '',
    this.color,
  });

  bool get hasPassword => password != null && password!.isNotEmpty;

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'content': content,
      'color': color?.value,
      'password': password,
    };
  }

  factory Note.fromMap(Map<String, dynamic> map) {
    return Note(
      id: map['id'],
      title: map['title'],
      content: map['content'] ?? '',
      color: map['color'] != null ? Color(map['color']) : null,
      password: map['password'],
    );
  }

  Note copyWith({
    int? id,
    String? title,
    String? password,
    String? content,
    Color? color,
  }) {
    return Note(
      id: id ?? this.id,
      title: title ?? this.title,
      password: password ?? this.password,
      content: content ?? this.content,
      color: color ?? this.color,
    );
  }
}

class NoteDetail extends StatefulWidget {
  final Note note;

  const NoteDetail({super.key, required this.note});

  @override
  _NoteDetailState createState() => _NoteDetailState();
}

class _NoteDetailState extends State<NoteDetail> {
  late TextEditingController _titleController;
  late TextEditingController _contentController;
  bool _isModified = false;
  Color? _selectedColor;

  @override
  void initState() {
    super.initState();
    _titleController = TextEditingController(text: widget.note.title);
    _contentController = TextEditingController(text: widget.note.content);
    _selectedColor = widget.note.color;
    _titleController.addListener(_checkModified);
    _contentController.addListener(_checkModified);
  }

  void _checkModified() {
    setState(() {
      _isModified =
          _titleController.text != widget.note.title ||
          _contentController.text != widget.note.content ||
          _selectedColor != widget.note.color;
    });
  }

  @override
  void dispose() {
    _titleController.dispose();
    _contentController.dispose();
    super.dispose();
  }

  Future<bool> _onWillPop() async {
    if (_isModified) {
      bool? shouldContinue = await showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: const Text('Lưu Thay Đổi'),
            content: const Text(
              'Ghi chú đã thay đổi, bạn muốn tiếp tục chỉnh sửa?',
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(context, false),
                child: const Text('Không'),
              ),
              TextButton(
                onPressed: () => Navigator.pop(context, true),
                child: const Text('Có'),
              ),
            ],
          );
        },
      );

      if (shouldContinue == false) {
        final updatedNote = widget.note.copyWith(
          title: _titleController.text,
          content: _contentController.text,
          color: _selectedColor,
        );
        Navigator.pop(context, updatedNote);
        return true;
      }
      return shouldContinue ?? false;
    }
    Navigator.pop(context, widget.note);
    return true;
  }

  void _changeNoteColor() {
    Color currentColor = _selectedColor ?? Colors.yellow;
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Chọn Màu Ghi Chú'),
          content: SingleChildScrollView(
            child: BlockPicker(
              pickerColor: currentColor,
              onColorChanged: (color) => currentColor = color,
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                setState(() {
                  _selectedColor = currentColor;
                  _isModified = true;
                });
                Navigator.pop(context);
              },
              child: const Text('Lưu'),
            ),
          ],
        );
      },
    );
  }

  void _editTitle() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Chỉnh Sửa Tiêu Đề'),
          content: TextField(
            controller: _titleController,
            decoration: const InputDecoration(labelText: 'Tiêu Đề'),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Hủy'),
            ),
            TextButton(
              onPressed: () {
                setState(() {
                  _isModified = true;
                });
                Navigator.pop(context);
              },
              child: const Text('Lưu'),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: _onWillPop,
      child: Scaffold(
        appBar: AppBar(
          title: GestureDetector(
            onTap: _editTitle,
            child: Text(_titleController.text),
          ),
          backgroundColor: Colors.purple,
          foregroundColor: Colors.white,
          actions: [
            IconButton(
              icon: const Icon(Icons.color_lens),
              onPressed: _changeNoteColor,
            ),
          ],
        ),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              TextField(
                controller: _contentController,
                decoration: const InputDecoration(
                  labelText: 'Nội Dung Ghi Chú',
                  border: OutlineInputBorder(),
                ),
                maxLines: 10,
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text('Màu Nền:'),
                  ElevatedButton(
                    onPressed: _changeNoteColor,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _selectedColor ?? Colors.yellow,
                    ),
                    child: const Text('Thay Đổi Màu'),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
