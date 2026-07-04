@if "%DEBUG%"=="" (
  @echo off
  setlocal
)

set DIRNAME=%~dp0
if "%DIRNAME%"=="" (
  set DIRNAME=.
)

"%DIRNAME%"gradlew.bat %*
@if "%DEBUG%"=="" (
  endlocal
)