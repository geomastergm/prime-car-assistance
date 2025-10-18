# Script to copy logos to launcher icon folders
# This will use the existing logos as launcher icons

Write-Host "🎨 Setting up launcher icons from existing logos..." -ForegroundColor Cyan
Write-Host ""

# Paths
$primeLogoPath = "Prime\app\src\main\res\drawable\logo_prime.png"
$partnerLogoPath = "Prime Partner\app\src\main\res\drawable\logo_prime_partner.png"

# Define mipmap folders
$mipmapFolders = @("mipmap-mdpi", "mipmap-hdpi", "mipmap-xhdpi", "mipmap-xxhdpi", "mipmap-xxxhdpi")

Write-Host "📱 Processing Prime app..." -ForegroundColor Blue

# For Prime app
foreach ($folder in $mipmapFolders) {
    $targetPath = "Prime\app\src\main\res\$folder"
    
    if (Test-Path $targetPath) {
        # Copy logo to ic_launcher
        Copy-Item $primeLogoPath "$targetPath\ic_launcher.png" -Force
        Copy-Item $primeLogoPath "$targetPath\ic_launcher_round.png" -Force
        Write-Host "  ✅ Copied to $folder" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "📱 Processing Prime Partner app..." -ForegroundColor Yellow

# For Prime Partner app
foreach ($folder in $mipmapFolders) {
    $targetPath = "Prime Partner\app\src\main\res\$folder"
    
    if (Test-Path $targetPath) {
        # Copy logo to ic_launcher
        Copy-Item $partnerLogoPath "$targetPath\ic_launcher.png" -Force
        Copy-Item $partnerLogoPath "$targetPath\ic_launcher_round.png" -Force
        Write-Host "  ✅ Copied to $folder" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "✅ Launcher icons updated successfully!" -ForegroundColor Green
Write-Host ""
Write-Host "🔧 Next steps:" -ForegroundColor Cyan
Write-Host "  1. Clean and rebuild projects" -ForegroundColor White
Write-Host "  2. Uninstall old apps from device/emulator" -ForegroundColor White
Write-Host "  3. Install new APKs" -ForegroundColor White
Write-Host ""
Write-Host "💡 Note: The XML adaptive icons are already configured with custom 'P' design" -ForegroundColor Yellow
Write-Host "   The PNG logos will be used as fallback icons" -ForegroundColor Yellow
