import 'package:flutter/material.dart';
import '../widgets/component_example.dart';

class AnimationsScreen extends StatefulWidget {
  const AnimationsScreen({super.key});

  @override
  State<AnimationsScreen> createState() => _AnimationsScreenState();
}

class _AnimationsScreenState extends State<AnimationsScreen>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;
  bool _isExpanded = false;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      duration: const Duration(seconds: 2),
      vsync: this,
    );
    _animation = CurvedAnimation(
      parent: _controller,
      curve: Curves.easeInOut,
    );
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Animations',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Implicit Animations',
              description: 'Animations that happen automatically when properties change.',
              example: Column(
                children: [
                  GestureDetector(
                    onTap: () => setState(() => _isExpanded = !_isExpanded),
                    child: AnimatedContainer(
                      duration: const Duration(milliseconds: 300),
                      width: _isExpanded ? 200 : 100,
                      height: _isExpanded ? 200 : 100,
                      decoration: BoxDecoration(
                        color: _isExpanded ? Colors.blue : Colors.red,
                        borderRadius: BorderRadius.circular(_isExpanded ? 100 : 8),
                      ),
                      child: Center(
                        child: Text(
                          'Tap me!',
                          style: const TextStyle(color: Colors.white),
                        ),
                      ),
                    ),
                  ),
                ],
              ),
              code: '''
AnimatedContainer(
  duration: const Duration(milliseconds: 300),
  width: _isExpanded ? 200 : 100,
  height: _isExpanded ? 200 : 100,
  decoration: BoxDecoration(
    color: _isExpanded ? Colors.blue : Colors.red,
    borderRadius: BorderRadius.circular(_isExpanded ? 100 : 8),
  ),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Explicit Animations',
              description: 'Animations controlled by an AnimationController.',
              example: Column(
                children: [
                  RotationTransition(
                    turns: _animation,
                    child: Container(
                      width: 100,
                      height: 100,
                      decoration: BoxDecoration(
                        color: Colors.green,
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: const Icon(Icons.refresh, color: Colors.white),
                    ),
                  ),
                  const SizedBox(height: 16),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      ElevatedButton(
                        onPressed: () {
                          if (_controller.isAnimating) {
                            _controller.stop();
                          } else {
                            _controller.repeat();
                          }
                        },
                        child: Text(_controller.isAnimating ? 'Stop' : 'Start'),
                      ),
                    ],
                  ),
                ],
              ),
              code: '''
RotationTransition(
  turns: _animation,
  child: Container(
    width: 100,
    height: 100,
    decoration: BoxDecoration(
      color: Colors.green,
      borderRadius: BorderRadius.circular(8),
    ),
  ),
),

// In initState:
_controller = AnimationController(
  duration: const Duration(seconds: 2),
  vsync: this,
);
_animation = CurvedAnimation(
  parent: _controller,
  curve: Curves.easeInOut,
);''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Hero Animation',
              description: 'Animations for transitioning widgets between screens.',
              example: GestureDetector(
                onTap: () {
                  Navigator.of(context).push(
                    MaterialPageRoute<void>(
                      builder: (BuildContext context) {
                        return Scaffold(
                          appBar: AppBar(),
                          body: Center(
                            child: Hero(
                              tag: 'hero-tag',
                              child: Container(
                                width: 300,
                                height: 300,
                                decoration: BoxDecoration(
                                  color: Colors.orange,
                                  borderRadius: BorderRadius.circular(16),
                                ),
                                child: const Center(
                                  child: Text(
                                    'Tap to go back',
                                    style: TextStyle(color: Colors.white),
                                  ),
                                ),
                              ),
                            ),
                          ),
                        );
                      },
                    ),
                  );
                },
                child: Hero(
                  tag: 'hero-tag',
                  child: Container(
                    width: 100,
                    height: 100,
                    decoration: BoxDecoration(
                      color: Colors.orange,
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: const Center(
                      child: Text(
                        'Tap me!',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ),
                ),
              ),
              code: '''
Hero(
  tag: 'hero-tag',
  child: Container(
    width: 100,
    height: 100,
    decoration: BoxDecoration(
      color: Colors.orange,
      borderRadius: BorderRadius.circular(8),
    ),
  ),
),

// On the destination screen:
Hero(
  tag: 'hero-tag',
  child: Container(
    width: 300,
    height: 300,
    decoration: BoxDecoration(
      color: Colors.orange,
      borderRadius: BorderRadius.circular(16),
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