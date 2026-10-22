@echo off
rem Dvojklik na .jar nema konzoli, takze program nemuze cist vstup. Tohle okno ji ma.
cd /d "%~dp0"
java -jar uctenka.jar
pause
