@echo off
echo Building Brothers Client App...

cd "C:\Users\user\AndroidStudioProjects\Brothers"
call gradlew.bat clean assembleDebug
copy "app\build\outputs\apk\debug\app-debug.apk" "..\Brothers-Client-Final.apk"

echo.
echo Building Brothers Provider App...

cd "C:\Users\user\AndroidStudioProjects\Brothers of brothers"
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr
call gradlew.bat clean assembleDebug
copy "app\build\outputs\apk\debug\app-debug.apk" "..\Brothers-Provider-Final.apk"

echo.
echo Both APKs built successfully!
dir /B C:\Users\user\AndroidStudioProjects\*.apk

pause