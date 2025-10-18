@echo off
echo ============================================
echo    Installing Brothers Apps via ADB
echo ============================================

echo Checking ADB connection...
adb devices

echo.
echo Installing Brothers (Client App)...
adb install -r "C:\Users\user\AndroidStudioProjects\Brothers\Emergency-Road-Assistant-SIGNED.apk"

echo.
echo Installing Brothers of Brothers (Provider App)...
adb install -r "C:\Users\user\AndroidStudioProjects\Brothers of brothers\app\build\outputs\apk\debug\app-debug.apk"

echo.
echo ============================================
echo Installation completed!
echo ============================================

echo Apps installed:
echo 📱 Brothers - Emergency Road Assistant (Client)
echo 👷 Brothers of Brothers - Service Provider
echo.
echo Check your phone for the installed apps!
pause