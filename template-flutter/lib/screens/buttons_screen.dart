import 'package:flutter/material.dart';
import '../widgets/component_example.dart';

class ButtonsScreen extends StatelessWidget {
  const ButtonsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Buttons',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Elevated Button',
              description: 'A Material Design elevated button. The default button style.',
              example: Wrap(
                spacing: 8,
                runSpacing: 8,
                children: [
                  ElevatedButton(
                    onPressed: () {},
                    child: const Text('Enabled'),
                  ),
                  ElevatedButton(
                    onPressed: null,
                    child: const Text('Disabled'),
                  ),
                  ElevatedButton.icon(
                    onPressed: () {},
                    icon: const Icon(Icons.add),
                    label: const Text('With Icon'),
                  ),
                ],
              ),
              code: '''
ElevatedButton(
  onPressed: () {},
  child: const Text('Enabled'),
),

ElevatedButton(
  onPressed: null,
  child: const Text('Disabled'),
),

ElevatedButton.icon(
  onPressed: () {},
  icon: const Icon(Icons.add),
  label: const Text('With Icon'),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Filled Button',
              description: 'A filled button with a more prominent look.',
              example: Wrap(
                spacing: 8,
                runSpacing: 8,
                children: [
                  FilledButton(
                    onPressed: () {},
                    child: const Text('Filled'),
                  ),
                  FilledButton.tonal(
                    onPressed: () {},
                    child: const Text('Tonal'),
                  ),
                  FilledButton.icon(
                    onPressed: () {},
                    icon: const Icon(Icons.add),
                    label: const Text('With Icon'),
                  ),
                ],
              ),
              code: '''
FilledButton(
  onPressed: () {},
  child: const Text('Filled'),
),

FilledButton.tonal(
  onPressed: () {},
  child: const Text('Tonal'),
),

FilledButton.icon(
  onPressed: () {},
  icon: const Icon(Icons.add),
  label: const Text('With Icon'),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Outlined Button',
              description: 'A Material Design outlined button, essentially a TextButton with an outline.',
              example: Wrap(
                spacing: 8,
                runSpacing: 8,
                children: [
                  OutlinedButton(
                    onPressed: () {},
                    child: const Text('Outlined'),
                  ),
                  OutlinedButton.icon(
                    onPressed: () {},
                    icon: const Icon(Icons.add),
                    label: const Text('With Icon'),
                  ),
                ],
              ),
              code: '''
OutlinedButton(
  onPressed: () {},
  child: const Text('Outlined'),
),

OutlinedButton.icon(
  onPressed: () {},
  icon: const Icon(Icons.add),
  label: const Text('With Icon'),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Text Button',
              description: 'A simple text button without elevation or outline.',
              example: Wrap(
                spacing: 8,
                runSpacing: 8,
                children: [
                  TextButton(
                    onPressed: () {},
                    child: const Text('Text Button'),
                  ),
                  TextButton.icon(
                    onPressed: () {},
                    icon: const Icon(Icons.info),
                    label: const Text('With Icon'),
                  ),
                ],
              ),
              code: '''
TextButton(
  onPressed: () {},
  child: const Text('Text Button'),
),

TextButton.icon(
  onPressed: () {},
  icon: const Icon(Icons.info),
  label: const Text('With Icon'),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Icon Button',
              description: 'A button that only contains an icon.',
              example: Wrap(
                spacing: 8,
                runSpacing: 8,
                children: [
                  IconButton(
                    onPressed: () {},
                    icon: const Icon(Icons.favorite),
                  ),
                  IconButton.filled(
                    onPressed: () {},
                    icon: const Icon(Icons.add),
                  ),
                  IconButton.filledTonal(
                    onPressed: () {},
                    icon: const Icon(Icons.edit),
                  ),
                  IconButton.outlined(
                    onPressed: () {},
                    icon: const Icon(Icons.delete),
                  ),
                ],
              ),
              code: '''
IconButton(
  onPressed: () {},
  icon: const Icon(Icons.favorite),
),

IconButton.filled(
  onPressed: () {},
  icon: const Icon(Icons.add),
),

IconButton.filledTonal(
  onPressed: () {},
  icon: const Icon(Icons.edit),
),

IconButton.outlined(
  onPressed: () {},
  icon: const Icon(Icons.delete),
),''',
            ),
          ],
        ),
      ),
    );
  }
} 