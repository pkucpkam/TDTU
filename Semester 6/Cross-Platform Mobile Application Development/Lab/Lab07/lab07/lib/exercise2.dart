import 'package:flutter/material.dart';
import 'package:flutter_slidable/flutter_slidable.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';

class Exercise2Screen extends StatefulWidget {
  const Exercise2Screen({super.key});

  @override
  _Exercise2ScreenState createState() => _Exercise2ScreenState();
}

class _Exercise2ScreenState extends State<Exercise2Screen> {
  List<Note> notes = [
    Note(title: 'Note 1'),
    Note(title: 'Note 2'),
    Note(title: 'Note 3'),
  ];
  bool isGridView = false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Note Management'),
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
          key: ValueKey(notes[index]),
          endActionPane: ActionPane(
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
          onLongPress: () => _showBottomSheet(notes[index]),
          child: Card(
            color: notes[index].color ?? Colors.yellow,
            child: Center(child: Text(notes[index].title)),
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

  void _addNote() {
    setState(() {
      notes.add(Note(title: 'New Note ${notes.length + 1}'));
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
              onPressed: () {
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
              onPressed: () {
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
              onPressed: () {
                if (confirmPassword != note.password) {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text('Password is incorrect')),
                  );
                  return;
                }
                setState(() {
                  note.password = null;
                });
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
              onPressed: () {
                setState(() {
                  notes.remove(note);
                });
                Navigator.pop(context);
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: const Text('Note deleted'),
                    action: SnackBarAction(
                      label: 'Undo',
                      onPressed: () {
                        setState(() {
                          notes.add(note);
                        });
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
              onPressed: () {
                setState(() {
                  note.color = selectedColor;
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
}

class Note {
  final String title;
  String? password;
  String content;
  Color? color;
  bool get hasPassword => password != null;

  Note({required this.title, this.password, this.color, this.content = ''});

  Note copyWith({
    String? title,
    String? password,
    String? content,
    Color? color,
  }) {
    return Note(
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
  late TextEditingController _controller;
  bool _isModified = false;
  Color? _selectedColor;

  @override
  void initState() {
    super.initState();
    _controller = TextEditingController(text: widget.note.content);
    _selectedColor = widget.note.color;
    _controller.addListener(() {
      setState(() {
        _isModified =
            _controller.text != widget.note.content ||
            _selectedColor != widget.note.color;
      });
    });
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Future<bool> _onWillPop() async {
    if (_isModified) {
      bool? shouldContinue = await showDialog(
        context: context,
        builder: (context) {
          return AlertDialog(
            title: const Text('Save Changes'),
            content: const Text(
              'Note is modified, do you want to continue editing?',
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

      if (shouldContinue == false) {
        final updatedNote = widget.note.copyWith(
          content: _controller.text,
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
                  _isModified =
                      _controller.text != widget.note.content ||
                      _selectedColor != widget.note.color;
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
          title: Text(widget.note.title),
          backgroundColor: Colors.purple,
          foregroundColor: Colors.white,
        ),
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            children: [
              TextField(
                controller: _controller,
                decoration: const InputDecoration(
                  labelText: 'Note Content',
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
            ],
          ),
        ),
      ),
    );
  }
}
