import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';
import 'database_helper.dart';
import 'note.dart';

class NotesApp extends StatelessWidget {
  const NotesApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: NotesScreen(),
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        primarySwatch: Colors.purple,
        scaffoldBackgroundColor: Colors.grey[200],
      ),
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
  bool isGridView = true;

  @override
  void initState() {
    super.initState();
    _loadNotes();
  }

  Future<void> _loadNotes() async {
    final loadedNotes = await DatabaseHelper.instance.getNotes();
    setState(() {
      notes = loadedNotes;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Note Management'),
        backgroundColor: Colors.blue,
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
                  label: 'Change',
                ),
                SlidableAction(
                  onPressed: (_) => _removePassword(notes[index]),
                  backgroundColor: Colors.cyan,
                  icon: Icons.lock_open,
                  label: 'Unprotect',
                ),
              ] else
                SlidableAction(
                  onPressed: (_) => _setPassword(notes[index]),
                  backgroundColor: Colors.cyan,
                  icon: Icons.lock,
                  label: 'Protect',
                ),
              SlidableAction(
                onPressed: (_) => _deleteNoteConfirmation(notes[index]),
                backgroundColor: Colors.red,
                icon: Icons.delete,
                label: 'Delete',
              ),
            ],
          ),
          child: ListTile(
            title: Text(notes[index].title),
            subtitle: Text(
              notes[index].content.length > 50
                  ? '${notes[index].content.substring(0, 50)}...'
                  : notes[index].content,
              style: TextStyle(color: Colors.grey[600]),
            ),
            trailing: notes[index].hasPassword ? const Icon(Icons.lock) : null,
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
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    notes[index].title,
                    style: const TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.bold,
                      color: Colors.red,
                    ),
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                    textAlign: TextAlign.center,
                  ),
                  const SizedBox(height: 8),
                  Text(
                    notes[index].content.length > 30
                        ? '${notes[index].content.substring(0, 30)}...'
                        : notes[index].content,
                    style: TextStyle(color: Colors.grey[800]),
                    maxLines: 3,
                    overflow: TextOverflow.ellipsis,
                    textAlign: TextAlign.justify,
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }

  void _toggleViewMode() {
    setState(() {
      isGridView = !isGridView;
    });
  }

  void _addNote() async {
    final newNote = Note(
      title: 'New Note ${notes.length + 1}',
      content: 'Content of new note ${notes.length + 1}',
    );
    final id = await DatabaseHelper.instance.insertNote(newNote);
    setState(() {
      notes.add(newNote.copyWith(id: id));
    });
  }

  void _setPassword(Note note) {
    String? newPassword;
    String? confirmPassword;

    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Set password'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                decoration: const InputDecoration(labelText: 'Password'),
                obscureText: true,
                onChanged: (value) {
                  newPassword = value;
                },
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'Confirm'),
                obscureText: true,
                onChanged: (value) {
                  confirmPassword = value;
                },
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                if (newPassword == null || newPassword!.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Password cannot be empty')),
                  );
                  return;
                }
                if (newPassword != confirmPassword) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Passwords do not match')),
                  );
                  return;
                }
                setState(() {
                  note.password = newPassword;
                });
                await DatabaseHelper.instance.updateNote(note);
                Navigator.pop(context);
              },
              child: const Text('Protect'),
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
      builder: (context) {
        return AlertDialog(
          title: const Text('Change password'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                decoration: const InputDecoration(labelText: 'Old Password'),
                obscureText: true,
                onChanged: (value) {
                  oldPassword = value;
                },
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'New Password'),
                obscureText: true,
                onChanged: (value) {
                  newPassword = value;
                },
              ),
              TextField(
                decoration: const InputDecoration(labelText: 'Confirm'),
                obscureText: true,
                onChanged: (value) {
                  confirmPassword = value;
                },
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                if (oldPassword != note.password) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Old password is incorrect')),
                  );
                  return;
                }
                if (newPassword == null || newPassword!.isEmpty) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(
                      content: Text('New password cannot be empty'),
                    ),
                  );
                  return;
                }
                if (newPassword != confirmPassword) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('New passwords do not match')),
                  );
                  return;
                }
                setState(() {
                  note.password = newPassword;
                });
                await DatabaseHelper.instance.updateNote(note);
                Navigator.pop(context);
              },
              child: const Text('Change Password'),
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
      builder: (context) {
        return AlertDialog(
          title: const Text('Remove Password'),
          content: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                decoration: const InputDecoration(labelText: 'Enter Password'),
                obscureText: true,
                onChanged: (value) {
                  confirmPassword = value;
                },
              ),
            ],
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                if (confirmPassword != note.password) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Password is incorrect')),
                  );
                  return;
                }
                setState(() {
                  note.password = null;
                });
                await DatabaseHelper.instance.updateNote(note);
                Navigator.pop(context);
              },
              child: const Text('Remove'),
            ),
          ],
        );
      },
    );
  }

  void _deleteNoteConfirmation(Note note) {
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Delete this note'),
          content: const Text('Are you sure you want to delete this note?'),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                final deletedNote = note;
                setState(() {
                  notes.remove(note);
                });
                await DatabaseHelper.instance.deleteNote(note.id!);
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: const Text('Note deleted'),
                    action: SnackBarAction(
                      label: 'Undo',
                      onPressed: () async {
                        setState(() {
                          notes.add(deletedNote);
                        });
                        await DatabaseHelper.instance.insertNote(deletedNote);
                      },
                    ),
                  ),
                );
              },
              child: const Text('Delete'),
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
        final index = notes.indexOf(note);
        if (index != -1) {
          notes[index] = updatedNote;
        }
      });
      await DatabaseHelper.instance.updateNote(updatedNote);
    }
  }

  Future<bool?> _passwordProtectedDialog(Note note) async {
    String? enteredPassword;

    return showDialog<bool>(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Enter password'),
          content: TextField(
            decoration: const InputDecoration(labelText: 'Password'),
            obscureText: true,
            onChanged: (value) {
              enteredPassword = value;
            },
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context, false),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                if (enteredPassword == note.password) {
                  Navigator.pop(context, true);
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Incorrect password')),
                  );
                }
              },
              child: const Text('Confirm'),
            ),
          ],
        );
      },
    );
  }

  void _showBottomSheet(Note note) {
    showModalBottomSheet(
      context: context,
      builder: (context) {
        return Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            ListTile(
              leading: const Icon(Icons.delete),
              title: const Text('Delete'),
              onTap: () {
                Navigator.pop(context);
                _deleteNoteConfirmation(note);
              },
            ),
            if (note.hasPassword) ...[
              ListTile(
                leading: const Icon(Icons.lock),
                title: const Text('Change Password'),
                onTap: () {
                  Navigator.pop(context);
                  _changePassword(note);
                },
              ),
              ListTile(
                leading: const Icon(Icons.lock_open),
                title: const Text('Remove Password'),
                onTap: () {
                  Navigator.pop(context);
                  _removePassword(note);
                },
              ),
            ] else
              ListTile(
                leading: const Icon(Icons.lock),
                title: const Text('Protect'),
                onTap: () {
                  Navigator.pop(context);
                  _setPassword(note);
                },
              ),
            ListTile(
              leading: const Icon(Icons.color_lens),
              title: const Text('Change Color'),
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
      builder: (context) {
        return AlertDialog(
          title: const Text('Choose Note Color'),
          content: SingleChildScrollView(
            child: BlockPicker(
              pickerColor: selectedColor,
              onColorChanged: (color) {
                selectedColor = color;
              },
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () async {
                setState(() {
                  note.color = selectedColor;
                });
                await DatabaseHelper.instance.updateNote(note);
                Navigator.pop(context);
              },
              child: const Text('Save'),
            ),
          ],
        );
      },
    );
  }
}

class NoteDetail extends StatefulWidget {
  final Note note;

  const NoteDetail({required this.note, super.key});

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
    _titleController.addListener(_checkIfModified);
    _contentController.addListener(_checkIfModified);
  }

  void _checkIfModified() {
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
      bool? shouldSave = await showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: const Text('Save Changes'),
            content: const Text(
              'You have unsaved changes. Do you want to save them?',
            ),
            actions: [
              TextButton(
                onPressed: () => Navigator.pop(context, false),
                child: const Text('No'),
              ),
              TextButton(
                onPressed: () => Navigator.pop(context, true),
                child: const Text('Yes'),
              ),
            ],
          );
        },
      );

      if (shouldSave == true) {
        _saveChanges();
      }
      return true;
    }
    return true;
  }

  void _saveChanges() {
    final updatedNote = widget.note.copyWith(
      title: _titleController.text,
      content: _contentController.text,
      color: _selectedColor,
    );
    Navigator.pop(context, updatedNote);
  }

  void _changeNoteColor() {
    Color currentColor = _selectedColor ?? Colors.yellow;
    showDialog(
      context: context,
      builder: (context) {
        return AlertDialog(
          title: const Text('Choose Note Color'),
          content: SingleChildScrollView(
            child: BlockPicker(
              pickerColor: currentColor,
              onColorChanged: (color) {
                currentColor = color;
              },
            ),
          ),
          actions: [
            TextButton(
              onPressed: () => Navigator.pop(context),
              child: const Text('Cancel'),
            ),
            TextButton(
              onPressed: () {
                setState(() {
                  _selectedColor = currentColor;
                  _checkIfModified();
                });
                Navigator.pop(context);
              },
              child: const Text('Save'),
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
          title: const Text('Edit a Note'),
          backgroundColor: Colors.blue,
          foregroundColor: Colors.white,
        ),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              TextField(
                controller: _titleController,
                decoration: const InputDecoration(
                  labelText: 'Title',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16),
              TextField(
                controller: _contentController,
                decoration: const InputDecoration(
                  labelText: 'Content',
                  border: OutlineInputBorder(),
                ),
                maxLines: 5,
              ),
              const SizedBox(height: 16),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text('Background Color:'),
                  ElevatedButton(
                    onPressed: _changeNoteColor,
                    style: ElevatedButton.styleFrom(
                      backgroundColor: _selectedColor ?? Colors.yellow,
                    ),
                    child: const Text('Change Color'),
                  ),
                ],
              ),
              const Spacer(),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: _isModified ? _saveChanges : null,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    disabledBackgroundColor: Colors.blue,
                    padding: const EdgeInsets.symmetric(
                      horizontal: 32,
                      vertical: 16,
                    ),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(5),
                    ),
                  ),
                  child: const Text(
                    'Save all changes',
                    style: TextStyle(fontSize: 16, color: Colors.white),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
