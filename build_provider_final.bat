@echo off
echo ============================================
echo   Building Brothers of Brothers (Provider)
echo ============================================

cd "C:\Users\user\AndroidStudioProjects\Brothers of brothers"

echo Cleaning previous builds...
call gradlew.bat clean

echo Building new APK with updated branding...
call gradlew.bat assembleDebug

echo Checking build output...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo SUCCESS: Brothers of Brothers APK created!
    echo Location: app\build\outputs\apk\debug\app-debug.apk
    
    REM Copy APK to main directory with new name
    copy "app\build\outputs\apk\debug\app-debug.apk" "Brothers-of-Brothers-Provider.apk"
    echo APK copied to: Brothers-of-Brothers-Provider.apk
    
    dir *.apk
) else (
    echo ERROR: APK build failed!
)

echo ============================================
echo Provider App build process completed!
echo ============================================
pause