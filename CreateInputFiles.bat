REM Created by chatGPT

@echo off
setlocal

REM Check if the argument is provided
if "%~1"=="" (
    echo Please provide a day number.
    exit /b
)

REM Assign the provided argument to a variable
set "day=%~1"

REM Create the files
echo Creating files: day%day%.txt and day%day%-example.txt
echo. > "src/main/resources/2023/provided/day%day%.txt"
echo. > "src/main/resources/2023/provided/day%day%-example.txt"

echo Files created successfully.
exit /b