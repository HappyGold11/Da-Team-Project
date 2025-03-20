@echo off
setlocal

REM Define variables
set PROJECT_NAME=f1database
set JAR_FILE=target\%PROJECT_NAME%-1.0-SNAPSHOT.jar

echo Building the project...
mvn clean package

if %ERRORLEVEL% NEQ 0 (
    echo Build failed! Exiting...
    exit /b %ERRORLEVEL%
)

echo Running the project...
java -jar %JAR_FILE%

endlocal
