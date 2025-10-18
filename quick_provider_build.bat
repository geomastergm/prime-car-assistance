@echo off
echo Building Brothers Provider App NOW...

cd /d "C:\Users\user\AndroidStudioProjects\Brothers of brothers"
set JAVA_HOME=C:\Program Files\Android\Android Studio\jbr

echo Starting build...
call gradlew.bat assembleDebug

echo Checking APK...
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo SUCCESS! APK built!
    copy "app\build\outputs\apk\debug\app-debug.apk" "C:\Users\user\AndroidStudioProjects\Brothers-Provider-Final.apk"
    dir "C:\Users\user\AndroidStudioProjects\Brothers-Provider-Final.apk"
) else (
    echo FAILED - APK not found
)