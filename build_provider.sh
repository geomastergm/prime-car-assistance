#!/bin/bash
# Provider App APK Builder Script

echo "=== Building Brothers of Brothers (Provider App) APK ==="

# Navigate to Provider app directory
cd "C:\Users\user\AndroidStudioProjects\Brothers of brothers"

# Build debug APK
echo "Starting Gradle build..."
./gradlew assembleDebug

echo "Build process completed!"
echo "APK should be in: app/build/outputs/apk/debug/"

# List generated APK files
if [ -d "app/build/outputs/apk/debug" ]; then
    echo "Generated APK files:"
    ls -la "app/build/outputs/apk/debug/"
else
    echo "APK directory not found. Build may have failed."
fi