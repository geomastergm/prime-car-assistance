@echo off
echo Installing Brothers Apps with Firebase fixes...
echo.

echo [1/3] Checking device connection...
adb devices

echo.
echo [2/3] Installing Client App...
adb install -r "Brothers-Client-Fixed.apk"

echo.
echo [3/3] Installing Provider Admin App...
adb install -r "Brothers-Provider-Fixed.apk"

echo.
echo Installation complete!
echo.
echo Test Steps:
echo 1. Open Provider App - Login with: azrikunikatuno / azrikunikatuno
echo 2. Open Client App - Create a service request
echo 3. Check Provider App for real-time request updates
echo.
pause