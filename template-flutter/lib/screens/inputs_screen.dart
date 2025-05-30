import 'package:flutter/material.dart';
import '../widgets/component_example.dart';

class InputsScreen extends StatefulWidget {
  const InputsScreen({super.key});

  @override
  State<InputsScreen> createState() => _InputsScreenState();
}

class _InputsScreenState extends State<InputsScreen> {
  bool _switch = false;
  bool _checkbox = false;
  double _slider = 0.5;
  DateTime _selectedDate = DateTime.now();
  TimeOfDay _selectedTime = TimeOfDay.now();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Inputs',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Text Field',
              description: 'Basic text input field with various styles.',
              example: Column(
                children: [
                  TextField(
                    decoration: const InputDecoration(
                      labelText: 'Basic TextField',
                      hintText: 'Enter some text',
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextField(
                    decoration: const InputDecoration(
                      border: OutlineInputBorder(),
                      labelText: 'Outlined TextField',
                      hintText: 'Enter some text',
                    ),
                  ),
                  const SizedBox(height: 16),
                  TextField(
                    decoration: const InputDecoration(
                      filled: true,
                      labelText: 'Filled TextField',
                      hintText: 'Enter some text',
                    ),
                  ),
                ],
              ),
              code: '''
TextField(
  decoration: const InputDecoration(
    labelText: 'Basic TextField',
    hintText: 'Enter some text',
  ),
),

TextField(
  decoration: const InputDecoration(
    border: OutlineInputBorder(),
    labelText: 'Outlined TextField',
    hintText: 'Enter some text',
  ),
),

TextField(
  decoration: const InputDecoration(
    filled: true,
    labelText: 'Filled TextField',
    hintText: 'Enter some text',
  ),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Switch and Checkbox',
              description: 'Toggle controls for boolean input.',
              example: Column(
                children: [
                  SwitchListTile(
                    title: const Text('Switch Example'),
                    value: _switch,
                    onChanged: (value) => setState(() => _switch = value),
                  ),
                  CheckboxListTile(
                    title: const Text('Checkbox Example'),
                    value: _checkbox,
                    onChanged: (value) => setState(() => _checkbox = value ?? false),
                  ),
                ],
              ),
              code: '''
SwitchListTile(
  title: const Text('Switch Example'),
  value: _switch,
  onChanged: (value) => setState(() => _switch = value),
),

CheckboxListTile(
  title: const Text('Checkbox Example'),
  value: _checkbox,
  onChanged: (value) => setState(() => _checkbox = value ?? false),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Slider',
              description: 'Continuous or discrete value selection.',
              example: Column(
                children: [
                  Slider(
                    value: _slider,
                    onChanged: (value) => setState(() => _slider = value),
                  ),
                  Slider(
                    value: _slider,
                    divisions: 5,
                    label: _slider.toString(),
                    onChanged: (value) => setState(() => _slider = value),
                  ),
                ],
              ),
              code: '''
Slider(
  value: _slider,
  onChanged: (value) => setState(() => _slider = value),
),

Slider(
  value: _slider,
  divisions: 5,
  label: _slider.toString(),
  onChanged: (value) => setState(() => _slider = value),
),''',
            ),
            const SizedBox(height: 16),
            ComponentExample(
              title: 'Date & Time Pickers',
              description: 'Material Design date and time selection dialogs.',
              example: Column(
                children: [
                  ListTile(
                    title: const Text('Selected Date'),
                    subtitle: Text(_selectedDate.toString().split(' ')[0]),
                    trailing: const Icon(Icons.calendar_today),
                    onTap: () async {
                      final date = await showDatePicker(
                        context: context,
                        initialDate: _selectedDate,
                        firstDate: DateTime(2000),
                        lastDate: DateTime(2100),
                      );
                      if (date != null) {
                        setState(() => _selectedDate = date);
                      }
                    },
                  ),
                  ListTile(
                    title: const Text('Selected Time'),
                    subtitle: Text(_selectedTime.format(context)),
                    trailing: const Icon(Icons.access_time),
                    onTap: () async {
                      final time = await showTimePicker(
                        context: context,
                        initialTime: _selectedTime,
                      );
                      if (time != null) {
                        setState(() => _selectedTime = time);
                      }
                    },
                  ),
                ],
              ),
              code: '''
ListTile(
  title: const Text('Selected Date'),
  subtitle: Text(_selectedDate.toString().split(' ')[0]),
  trailing: const Icon(Icons.calendar_today),
  onTap: () async {
    final date = await showDatePicker(
      context: context,
      initialDate: _selectedDate,
      firstDate: DateTime(2000),
      lastDate: DateTime(2100),
    );
    if (date != null) {
      setState(() => _selectedDate = date);
    }
  },
),

ListTile(
  title: const Text('Selected Time'),
  subtitle: Text(_selectedTime.format(context)),
  trailing: const Icon(Icons.access_time),
  onTap: () async {
    final time = await showTimePicker(
      context: context,
      initialTime: _selectedTime,
    );
    if (time != null) {
      setState(() => _selectedTime = time);
    }
  },
),''',
            ),
          ],
        ),
      ),
    );
  }
} 