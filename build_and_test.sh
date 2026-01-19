#!/bin/bash

echo "Building ChessTrainer Android App..."

# Change to project directory
cd /Users/admin/Downloads/VSCode/ChessTrainer

# Make sure gradlew is executable
chmod +x ./gradlew

echo "Running Gradle build check..."
./gradlew check

echo "Building debug APK..."
./gradlew assembleDebug

if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "Build successful! APK located at: app/build/outputs/apk/debug/app-debug.apk"
    ls -la app/build/outputs/apk/debug/app-debug.apk
else
    echo "Build failed!"
    exit 1
fi

echo "Build process completed."