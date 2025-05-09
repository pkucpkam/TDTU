import 'package:flutter/material.dart';

class Note {
  final int? id;
  final String title;
  String content;
  String? password;
  Color? color;
  bool get hasPassword => password != null;

  Note({
    this.id,
    required this.title,
    required this.content,
    this.password,
    this.color,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'content': content,
      'password': password,
      'color': color?.value,
    };
  }

  factory Note.fromMap(Map<String, dynamic> map) {
    return Note(
      id: map['id'],
      title: map['title'],
      content: map['content'],
      password: map['password'],
      color: map['color'] != null ? Color(map['color']) : null,
    );
  }

  Note copyWith({
    int? id,
    String? title,
    String? content,
    String? password,
    Color? color,
  }) {
    return Note(
      id: id ?? this.id,
      title: title ?? this.title,
      content: content ?? this.content,
      password: password ?? this.password,
      color: color ?? this.color,
    );
  }
}
