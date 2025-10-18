@echo off
echo ============================================
echo     Building Brothers App (Client App)
echo ============================================

cd "C:\Users\user\AndroidStudioProjects\Brothers"

echo Cleaning previous builds...
call gradlew.bat clean

echo Building new APK with updated branding...
call gradlew.bat assembleDebug

echo Checking build output...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo SUCCESS: Brothers APK created!
    echo Location: app\build\outputs\apk\debug\app-debug.apk
    
    REM Copy APK to main directory with new name
    copy "app\build\outputs\apk\debug\app-debug.apk" "Brothers-v2.apk"
    echo APK copied to: Brothers-v2.apk
    
    dir *.apk
) else (
    echo ERROR: APK build failed!
)

echo ============================================
echo Brothers App build process completed!
echo ============================================
pause