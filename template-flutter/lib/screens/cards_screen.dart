import 'package:flutter/material.dart';
import '../widgets/component_example.dart';

class CardsScreen extends StatelessWidget {
  const CardsScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Cards',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Basic Card',
              description: 'A basic Material Design card with some content.',
              example: Card(
                child: Padding(
                  padding: const EdgeInsets.all(16),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        'Basic Card Title',
                        style: Theme.of(context).textTheme.titleLarge,
                      ),
                      const SizedBox(height: 8),
                      Text(
                        'This is a basic card with some text content. Cards can contain any widget.',
                        style: Theme.of(context).textTheme.bodyMedium,
                      ),
                    ],
                  ),
                ),
              ),
              code: '''
Card(
  child: Padding(
    padding: const EdgeInsets.all(16),
    child: Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(
          'Basic Card Title',
          style: Theme.of(context).textTheme.titleLarge,
        ),
        const SizedBox(height: 8),
        Text(
          'This is a basic card with some text content. Cards can contain any widget.',
          style: Theme.of(context).textTheme.bodyMedium,
        ),
      ],
    ),
  ),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Card with Image',
              description: 'A card that includes an image and action buttons.',
              example: Card(
                clipBehavior: Clip.antiAlias,
                child: Column(
                  children: [
                    Image.network(
                      'https://picsum.photos/seed/picsum/400/200',
                      height: 200,
                      width: double.infinity,
                      fit: BoxFit.cover,
                    ),
                    Padding(
                      padding: const EdgeInsets.all(16),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(
                            'Card with Image',
                            style: Theme.of(context).textTheme.titleLarge,
                          ),
                          const SizedBox(height: 8),
                          Text(
                            'This card includes an image and some action buttons below.',
                            style: Theme.of(context).textTheme.bodyMedium,
                          ),
                        ],
                      ),
                    ),
                    ButtonBar(
                      alignment: MainAxisAlignment.start,
                      children: [
                        TextButton(
                          onPressed: () {},
                          child: const Text('ACTION 1'),
                        ),
                        TextButton(
                          onPressed: () {},
                          child: const Text('ACTION 2'),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              code: '''
Card(
  clipBehavior: Clip.antiAlias,
  child: Column(
    children: [
      Image.network(
        'https://picsum.photos/seed/picsum/400/200',
        height: 200,
        width: double.infinity,
        fit: BoxFit.cover,
      ),
      Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Card with Image',
              style: Theme.of(context).textTheme.titleLarge,
            ),
            const SizedBox(height: 8),
            Text(
              'This card includes an image and some action buttons below.',
              style: Theme.of(context).textTheme.bodyMedium,
            ),
          ],
        ),
      ),
      ButtonBar(
        alignment: MainAxisAlignment.start,
        children: [
          TextButton(
            onPressed: () {},
            child: const Text('ACTION 1'),
          ),
          TextButton(
            onPressed: () {},
            child: const Text('ACTION 2'),
          ),
        ],
      ),
    ],
  ),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Outlined Card',
              description: 'A card with an outline instead of elevation.',
              example: Card(
                elevation: 0,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(12),
                  side: BorderSide(
                    color: Theme.of(context).colorScheme.outline,
                  ),
                ),
                child: const ListTile(
                  leading: Icon(Icons.info),
                  title: Text('Outlined Card'),
                  subtitle: Text('This card uses an outline instead of elevation'),
                  trailing: Icon(Icons.arrow_forward_ios),
                ),
              ),
              code: '''
Card(
  elevation: 0,
  shape: RoundedRectangleBorder(
    borderRadius: BorderRadius.circular(12),
    side: BorderSide(
      color: Theme.of(context).colorScheme.outline,
    ),
  ),
  child: const ListTile(
    leading: Icon(Icons.info),
    title: Text('Outlined Card'),
    subtitle: Text('This card uses an outline instead of elevation'),
    trailing: Icon(Icons.arrow_forward_ios),
  ),
),''',
            ),
          ],
        ),
      ),
    );
  }
} 