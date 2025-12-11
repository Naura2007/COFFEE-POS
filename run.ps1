# run.ps1 - Compile & Run Coffee POS

# Pindah ke folder project (sesuaikan jika perlu)
cd "D:\Downloads\COFFEE-POS"

# Buat folder bin jika belum ada
if (-not (Test-Path bin)) { mkdir bin }

# Ambil semua file .java di src
$files = Get-ChildItem -Recurse src\*.java | ForEach-Object { $_.FullName }

# Compile semua file
Write-Host "Compiling project..."
javac -d bin -cp "libs/flatlaf-3.7.jar;libs/gson-2.10.1.jar" $files

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compile sukses! Running Main..."
    # Run Main.java
    java -cp "bin;libs/flatlaf-3.7.jar;libs/gson-2.10.1.jar" Main
} else {
    Write-Host "Compile gagal. Periksa error di atas."
}
