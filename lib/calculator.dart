import 'package:flutter/material.dart';

class Calculator extends StatefulWidget {
  final String title;
  Calculator({Key key, this.title}) : super(key: key);

  @override
  _CalculatorState createState() => _CalculatorState();
}

class _CalculatorState extends State<Calculator> {

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.all(10),
      child: Container(
        child: Text("Calculator"),
      ),
    );
  }
}
