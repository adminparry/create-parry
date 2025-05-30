import 'package:flutter/material.dart';
import '../widgets/component_example.dart';

class NavigationScreen extends StatefulWidget {
  const NavigationScreen({super.key});

  @override
  State<NavigationScreen> createState() => _NavigationScreenState();
}

class _NavigationScreenState extends State<NavigationScreen> {
  int _selectedIndex = 0;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Navigation',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Bottom Navigation Bar',
              description: 'A material design bottom navigation bar.',
              example: Container(
                height: 200,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Scaffold(
                  body: Center(
                    child: Text('Selected index: $_selectedIndex'),
                  ),
                  bottomNavigationBar: NavigationBar(
                    onDestinationSelected: (int index) {
                      setState(() {
                        _selectedIndex = index;
                      });
                    },
                    selectedIndex: _selectedIndex,
                    destinations: const <NavigationDestination>[
                      NavigationDestination(
                        icon: Icon(Icons.home),
                        label: 'Home',
                      ),
                      NavigationDestination(
                        icon: Icon(Icons.business),
                        label: 'Business',
                      ),
                      NavigationDestination(
                        icon: Icon(Icons.school),
                        label: 'School',
                      ),
                    ],
                  ),
                ),
              ),
              code: '''
NavigationBar(
  onDestinationSelected: (int index) {
    setState(() {
      _selectedIndex = index;
    });
  },
  selectedIndex: _selectedIndex,
  destinations: const <NavigationDestination>[
    NavigationDestination(
      icon: Icon(Icons.home),
      label: 'Home',
    ),
    NavigationDestination(
      icon: Icon(Icons.business),
      label: 'Business',
    ),
    NavigationDestination(
      icon: Icon(Icons.school),
      label: 'School',
    ),
  ],
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Navigation Rail',
              description: 'A material design navigation rail for side navigation.',
              example: Container(
                height: 300,
                decoration: BoxDecoration(
                  border: Border.all(color: Colors.grey),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: Row(
                  children: [
                    NavigationRail(
                      selectedIndex: _selectedIndex,
                      onDestinationSelected: (int index) {
                        setState(() {
                          _selectedIndex = index;
                        });
                      },
                      labelType: NavigationRailLabelType.selected,
                      destinations: const <NavigationRailDestination>[
                        NavigationRailDestination(
                          icon: Icon(Icons.favorite_border),
                          selectedIcon: Icon(Icons.favorite),
                          label: Text('First'),
                        ),
                        NavigationRailDestination(
                          icon: Icon(Icons.bookmark_border),
                          selectedIcon: Icon(Icons.book),
                          label: Text('Second'),
                        ),
                        NavigationRailDestination(
                          icon: Icon(Icons.star_border),
                          selectedIcon: Icon(Icons.star),
                          label: Text('Third'),
                        ),
                      ],
                    ),
                    const VerticalDivider(thickness: 1, width: 1),
                    Expanded(
                      child: Center(
                        child: Text('Selected index: $_selectedIndex'),
                      ),
                    )
                  ],
                ),
              ),
              code: '''
NavigationRail(
  selectedIndex: _selectedIndex,
  onDestinationSelected: (int index) {
    setState(() {
      _selectedIndex = index;
    });
  },
  labelType: NavigationRailLabelType.selected,
  destinations: const <NavigationRailDestination>[
    NavigationRailDestination(
      icon: Icon(Icons.favorite_border),
      selectedIcon: Icon(Icons.favorite),
      label: Text('First'),
    ),
    NavigationRailDestination(
      icon: Icon(Icons.bookmark_border),
      selectedIcon: Icon(Icons.book),
      label: Text('Second'),
    ),
    NavigationRailDestination(
      icon: Icon(Icons.star_border),
      selectedIcon: Icon(Icons.star),
      label: Text('Third'),
    ),
  ],
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Tabs',
              description: 'Material Design tabs for switching between views.',
              example: DefaultTabController(
                length: 3,
                child: Container(
                  height: 200,
                  decoration: BoxDecoration(
                    border: Border.all(color: Colors.grey),
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: Scaffold(
                    appBar: PreferredSize(
                      preferredSize: const Size.fromHeight(kToolbarHeight),
                      child: AppBar(
                        bottom: const TabBar(
                          tabs: [
                            Tab(text: 'TAB 1'),
                            Tab(text: 'TAB 2'),
                            Tab(text: 'TAB 3'),
                          ],
                        ),
                      ),
                    ),
                    body: const TabBarView(
                      children: [
                        Center(child: Text('Content of Tab 1')),
                        Center(child: Text('Content of Tab 2')),
                        Center(child: Text('Content of Tab 3')),
                      ],
                    ),
                  ),
                ),
              ),
              code: '''
DefaultTabController(
  length: 3,
  child: Scaffold(
    appBar: AppBar(
      bottom: const TabBar(
        tabs: [
          Tab(text: 'TAB 1'),
          Tab(text: 'TAB 2'),
          Tab(text: 'TAB 3'),
        ],
      ),
    ),
    body: const TabBarView(
      children: [
        Center(child: Text('Content of Tab 1')),
        Center(child: Text('Content of Tab 2')),
        Center(child: Text('Content of Tab 3')),
      ],
    ),
  ),
),''',
            ),
          ],
        ),
      ),
    );
  }
} 