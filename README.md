SimpleLedButton project
=====================================

Implements a push button than turns an LED on and off. 


Pre-requisites
--------------

- Android Studio 2.2+
- Designed for Raspberry Pi 3
- Electronic components:
 - 2 resistors
 - 1 push button
 - 1 LED

Build and install
=================

Mounting Electronic components
---------------------

- Connect one side of the button to GPIO PIN 21 PIN (BCM21) and the other side to ground
- Connect LED positive to GPIO PIN 6 (BCM6) and the other side to ground


Compiling and runing the project
--------------------------------

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, type

```bash
./gradlew installDebug
adb shell am start com.example.androidthings.myproject/.MainActivity
```
