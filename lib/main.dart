import 'package:flutter/material.dart';
import 'package:range_tuul/calculator.dart';
import 'package:range_tuul/classifiers.dart';

void main() {
  FlutterError.onError = (FlutterErrorDetails details) {
    FlutterError.dumpErrorToConsole(details);
  };
  runApp(Main());
}

class Main extends StatefulWidget {
  Main({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MainState createState() => _MainState();
}

class _MainState extends State<Main>
    with SingleTickerProviderStateMixin {
  String _title = "Calculator";

  @override
  void initState() {
    super.initState();
    _title = "Home";
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Range Tuul",
      theme: ThemeData(
        primarySwatch: Colors.grey
      ),
        home: Container(
          child: DefaultTabController(
              length: 2,
              child: Scaffold(
                  appBar: AppBar(
                    automaticallyImplyLeading: false,
                    title: Text(_title),
                  ),
                  bottomNavigationBar: menu(),
                  body: new TabBarView(
                    children: <Widget>[
                      Calculator(),
                      Classifiers()
                    ],
                  ))),
        )
    );
  }

  Widget menu() {
    return Container(
      color: Colors.white,
      child: TabBar(
        labelColor: Color.fromRGBO(66, 99, 66, 1.0),
        unselectedLabelColor: Colors.black54,
        indicatorSize: TabBarIndicatorSize.tab,
        indicatorColor: Colors.white,
        labelPadding: EdgeInsets.only(bottom: 20, left: 0, right: 0, top: 10),
        onTap: (int index) {
          _handleTap(index);
        },
//        controller: _tabController,
        tabs: [
          Tab(
            text: "Calculator",
            icon: Icon(Icons.home, size: 32),
          ),
          Tab(
            text: "Classifiers",
            icon: Icon(Icons.home, size: 32),
          ),
        ],
      ),
    );
  }

  void _handleTap(int index) {
    setState(() {
      switch (index) {
        case 0:
          {
            _title = "Calculator";
            break;
          }
      }
    });
  }
}
