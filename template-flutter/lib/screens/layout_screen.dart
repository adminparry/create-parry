import 'package:flutter/material.dart';
import 'package:flutter_staggered_grid_view/flutter_staggered_grid_view.dart';
import '../widgets/component_example.dart';

class LayoutScreen extends StatelessWidget {
  const LayoutScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Layout',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Row & Column',
              description: 'Basic row and column layouts with different alignments.',
              example: Column(
                children: [
                  Container(
                    height: 60,
                    color: Colors.grey[200],
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Container(
                          width: 50,
                          height: 50,
                          color: Colors.blue,
                        ),
                        Container(
                          width: 50,
                          height: 50,
                          color: Colors.red,
                        ),
                        Container(
                          width: 50,
                          height: 50,
                          color: Colors.green,
                        ),
                      ],
                    ),
                  ),
                  const SizedBox(height: 16),
                  Container(
                    width: 60,
                    height: 200,
                    color: Colors.grey[200],
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: [
                        Container(
                          width: 50,
                          height: 50,
                          color: Colors.blue,
                        ),
                        Container(
                          width: 50,
                          height: 50,
                          color: Colors.red,
                        ),
                        Container(
                          width: 50,
                          height: 50,
                          color: Colors.green,
                        ),
                      ],
                    ),
                  ),
                ],
              ),
              code: '''
Row(
  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
  children: [
    Container(
      width: 50,
      height: 50,
      color: Colors.blue,
    ),
    Container(
      width: 50,
      height: 50,
      color: Colors.red,
    ),
    Container(
      width: 50,
      height: 50,
      color: Colors.green,
    ),
  ],
),

Column(
  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
  children: [
    Container(
      width: 50,
      height: 50,
      color: Colors.blue,
    ),
    Container(
      width: 50,
      height: 50,
      color: Colors.red,
    ),
    Container(
      width: 50,
      height: 50,
      color: Colors.green,
    ),
  ],
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Stack',
              description: 'Stack layout for overlapping widgets.',
              example: Container(
                height: 200,
                color: Colors.grey[200],
                child: Stack(
                  children: [
                    Container(
                      width: 200,
                      height: 200,
                      color: Colors.blue.withOpacity(0.5),
                    ),
                    Positioned(
                      left: 40,
                      top: 40,
                      child: Container(
                        width: 120,
                        height: 120,
                        color: Colors.red.withOpacity(0.5),
                      ),
                    ),
                    Positioned(
                      right: 40,
                      bottom: 40,
                      child: Container(
                        width: 80,
                        height: 80,
                        color: Colors.green.withOpacity(0.5),
                      ),
                    ),
                  ],
                ),
              ),
              code: '''
Stack(
  children: [
    Container(
      width: 200,
      height: 200,
      color: Colors.blue.withOpacity(0.5),
    ),
    Positioned(
      left: 40,
      top: 40,
      child: Container(
        width: 120,
        height: 120,
        color: Colors.red.withOpacity(0.5),
      ),
    ),
    Positioned(
      right: 40,
      bottom: 40,
      child: Container(
        width: 80,
        height: 80,
        color: Colors.green.withOpacity(0.5),
      ),
    ),
  ],
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Grid',
              description: 'Grid layouts using StaggeredGridView.',
              example: Container(
                height: 300,
                color: Colors.grey[200],
                child: MasonryGridView.count(
                  crossAxisCount: 2,
                  mainAxisSpacing: 4,
                  crossAxisSpacing: 4,
                  itemBuilder: (context, index) {
                    return Container(
                      height: (index % 5 + 1) * 50,
                      color: Colors.primaries[index % Colors.primaries.length],
                      child: Center(
                        child: Text(
                          'Item $index',
                          style: const TextStyle(color: Colors.white),
                        ),
                      ),
                    );
                  },
                  itemCount: 10,
                ),
              ),
              code: '''
MasonryGridView.count(
  crossAxisCount: 2,
  mainAxisSpacing: 4,
  crossAxisSpacing: 4,
  itemBuilder: (context, index) {
    return Container(
      height: (index % 5 + 1) * 50,
      color: Colors.primaries[index % Colors.primaries.length],
      child: Center(
        child: Text(
          'Item \$index',
          style: const TextStyle(color: Colors.white),
        ),
      ),
    );
  },
  itemCount: 10,
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Wrap',
              description: 'Wrap layout for flowing content.',
              example: Container(
                color: Colors.grey[200],
                child: Wrap(
                  spacing: 8,
                  runSpacing: 8,
                  children: List.generate(
                    8,
                    (index) => Container(
                      width: 80,
                      height: 40,
                      color: Colors.primaries[index % Colors.primaries.length],
                      child: Center(
                        child: Text(
                          'Item $index',
                          style: const TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                  ),
                ),
              ),
              code: '''
Wrap(
  spacing: 8,
  runSpacing: 8,
  children: List.generate(
    8,
    (index) => Container(
      width: 80,
      height: 40,
      color: Colors.primaries[index % Colors.primaries.length],
      child: Center(
        child: Text(
          'Item \$index',
          style: const TextStyle(color: Colors.white),
        ),
      ),
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