@echo off
chcp 65001 >nul 2>&1

echo ============================================
echo    Building Brothers (Client) App v2.0
echo ============================================

cd /d "C:\Users\user\AndroidStudioProjects\Brothers"

echo Cleaning project...
call gradlew.bat clean

if %errorlevel% neq 0 (
    echo Error during clean. Continuing...
)

echo Building APK...
call gradlew.bat assembleDebug

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo ✅ SUCCESS: APK created!
    copy "app\build\outputs\apk\debug\app-debug.apk" "Brothers-Client-Final.apk" >nul
    echo 📱 APK Location: Brothers-Client-Final.apk
    echo 📊 File size:
    dir "Brothers-Client-Final.apk" | find ".apk"
) else (
    echo ❌ ERROR: APK not found!
    echo Checking debug folder...
    if exist "app\build\outputs\apk\debug\" (
        dir "app\build\outputs\apk\debug\"
    ) else (
        echo Debug folder doesn't exist
    )
)

echo ============================================
pause