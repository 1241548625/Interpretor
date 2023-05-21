# Simple Interpretor

Java SDK: 20.0.1
IDE: Intellij

To run the project, load the project file with Intellij.

## Or, run the via command line
1. Compile:
```bash
javac -Xlint:all -d ./build ./src/*.java ./src/module/*.java
```

2. Run:
```bash
java -classpath ./build Main code.txt
```