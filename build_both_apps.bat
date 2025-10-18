@echo off
echo ============================================
echo      Building Both Brothers Apps v2.0
echo ============================================

echo.
echo [1/2] Building Brothers (Client App)...
echo ----------------------------------------

cd "C:\Users\user\AndroidStudioProjects\Brothers"
call gradlew.bat clean
call gradlew.bat assembleDebug

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    copy "app\build\outputs\apk\debug\app-debug.apk" "Brothers-Client-v2.apk"
    echo ✅ Brothers Client APK created: Brothers-Client-v2.apk
) else (
    echo ❌ Brothers Client build failed!
)

echo.
echo [2/2] Building Brothers of Brothers (Provider App)...
echo ------------------------------------------------

cd "C:\Users\user\AndroidStudioProjects\Brothers of brothers"
call gradlew.bat clean
call gradlew.bat assembleDebug

if exist "app\build\outputs\apk\debug\app-debug.apk" (
    copy "app\build\outputs\apk\debug\app-debug.apk" "Brothers-Provider-v2.apk"
    echo ✅ Brothers Provider APK created: Brothers-Provider-v2.apk
) else (
    echo ❌ Brothers Provider build failed!
)

echo.
echo ============================================
echo           BUILD SUMMARY
echo ============================================

cd "C:\Users\user\AndroidStudioProjects"

echo 📱 Client App APKs:
dir "Brothers\Brothers-Client-v2.apk" 2>nul
dir "Brothers\Emergency-Road-Assistant*.apk" 2>nul

echo.
echo 👷 Provider App APKs:
dir "Brothers of brothers\Brothers-Provider-v2.apk" 2>nul

echo.
echo ============================================
echo Build process completed!
echo ============================================
pause