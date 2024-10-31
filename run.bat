@echo off
IF NOT EXIST target\*.jar (
    echo Сборка проекта...
    call mvn clean package
)
java -Dfile.encoding=cp866 -cp target\ecosystem-1.0-SNAPSHOT.jar org.example.Main
pause
