@echo off
echo Compilando código e testes...

:: Cria a pasta bin se não existir
if not exist bin mkdir bin

:: Compila todas as classes necessárias
javac -cp "lib/*" -d bin service\*.java test\*.java dao\*.java table\*.java util\*.java

if %ERRORLEVEL% NEQ 0 (
    echo ❌ Erro na compilação.
    pause
    exit /b %ERRORLEVEL%
)

echo ✅ Compilação bem-sucedida.
echo 🔍 Executando os testes...

java -jar lib\junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-classpath

pause
