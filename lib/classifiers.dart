import 'package:flutter/material.dart';

class Classifiers extends StatefulWidget {
  final String title;
  Classifiers({Key key, this.title}) : super(key: key);

  @override
  _ClassifiersState createState() => _ClassifiersState();
}

class _ClassifiersState extends State<Classifiers> {

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(10),
      child: Container(
        child: Text("Classifiers"),
      ),
    );
  }
}
