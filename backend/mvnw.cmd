@echo off
setlocal
where mvn >nul 2>&1
if %errorlevel% equ 0 (
  call mvn %*
  exit /b
)
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0.mvn\bootstrap.ps1" %*
exit /b %errorlevel%
