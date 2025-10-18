@echo off
echo Building Provider App APK...
cd "C:\Users\user\AndroidStudioProjects\Brothers of brothers"
call gradlew.bat assembleDebug
echo Build completed!
pause