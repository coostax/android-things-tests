# android-things-tests

Collection of tests i've made on android things

Uses a modified version of [Andriod Things Empty Project Template] (https://github.com/androidthings/new-project-template).
The master branch contains the modified base project template.
Each test was developed under this base template and is set on its own branch.


Pre-requisites
--------------

- Android Things compatible board
- Android Studio 2.2+


Build and install
=================

On Android Studio, click on the "Run" button.

If you prefer to run on the command line, type

```bash
./gradlew installDebug
adb shell am start pt.coostax.android.things.myproject/.MainActivity
```
